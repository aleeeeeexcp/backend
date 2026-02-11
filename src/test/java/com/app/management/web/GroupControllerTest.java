package com.app.management.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.management.bean.GroupDto;
import com.app.management.model.Expense;
import com.app.management.model.Group;
import com.app.management.model.Income;
import com.app.management.service.ExpenseService;
import com.app.management.service.GroupService;
import com.app.management.service.IncomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private GroupService groupService;

    @SuppressWarnings("removal")
    @MockBean
    private ExpenseService expenseService;

    @SuppressWarnings("removal")
    @MockBean
    private IncomeService incomeService;

    private GroupDto createGroupDto(String id, String name, String description, String ownerId, List<String> memberIds) {
        return GroupDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .ownerId(ownerId)
                .memberIds(memberIds)
                .build();
    }

    private Group createGroup(String id, String name, String description, String ownerId, List<String> memberIds, String createdAt) {
        return Group.builder()
                .id(id)
                .name(name)
                .description(description)
                .ownerId(ownerId)
                .memberIds(memberIds)
                .createdAt(createdAt)
                .build();
    }

    private Expense createExpense(String id, String description, double amount, String date, String userId, String groupId) {
        return Expense.builder()
                .id(id)
                .description(description)
                .amount(amount)
                .date(date)
                .userId(userId)
                .groupId(groupId)
                .build();
    }

    private Income createIncome(String id, String source, double amount, String description, String date, String userId, String groupId) {
        return Income.builder()
                .id(id)
                .source(source)
                .amount(amount)
                .description(description)
                .date(date)
                .userId(userId)
                .groupId(groupId)
                .build();
    }

    @SuppressWarnings("null")
    @Test
    void createGroup_returnsCreatedGroupDto() throws Exception {
        GroupDto groupDto = createGroupDto(null, "Family", "Family group", null, List.of("user1"));
        Group createdGroup = createGroup("g1", "Family", "Family group", "user1", List.of("user1"), "2026-02-11T10:00:00");

        when(groupService.createGroup(any(Group.class))).thenReturn(createdGroup);

        mockMvc.perform(post("/api/groups/createGroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("g1"))
                .andExpect(jsonPath("$.name").value("Family"))
                .andExpect(jsonPath("$.description").value("Family group"))
                .andExpect(jsonPath("$.ownerId").value("user1"))
                .andExpect(jsonPath("$.createdAt").value("2026-02-11T10:00:00"));
    }

    @Test
    void getAllGroups_returnsListOfGroupDtos() throws Exception {
        List<Group> groups = List.of(
                createGroup("g1", "Family", "Family group", "user1", List.of("user1", "user2"), "2026-02-11T10:00:00"),
                createGroup("g2", "Work", "Work group", "user2", List.of("user2", "user3"), "2026-02-11T11:00:00")
        );
        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("g1"))
                .andExpect(jsonPath("$[0].name").value("Family"))
                .andExpect(jsonPath("$[0].ownerId").value("user1"))
                .andExpect(jsonPath("$[1].id").value("g2"))
                .andExpect(jsonPath("$[1].name").value("Work"))
                .andExpect(jsonPath("$[1].ownerId").value("user2"));
    }

    @SuppressWarnings("null")
    @Test
    void getUserGroups_returnsUserGroupDtos() throws Exception {
        List<Group> groups = List.of(
                createGroup("g1", "Family", "Family group", "user1", List.of("user1", "user2"), "2026-02-11T10:00:00")
        );
        when(groupService.getUserGroups()).thenReturn(groups);

        mockMvc.perform(get("/api/groups/myGroups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("g1"))
                .andExpect(jsonPath("$[0].name").value("Family"));
    }

    @Test
    void getExpensesByGroup_returnsListOfExpenseDtos() throws Exception {
        String groupId = "g1";
        List<Expense> expenses = List.of(
                createExpense("e1", "Groceries", 50.0, "2024-01-01", "user1", groupId),
                createExpense("e2", "Dinner", 30.0, "2024-01-02", "user1", groupId)
        );
        when(expenseService.getExpensesByGroup(groupId)).thenReturn(expenses);

        mockMvc.perform(get("/api/groups/{groupId}/expenses", groupId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[0].description").value("Groceries"))
                .andExpect(jsonPath("$[0].amount").value(50.0))
                .andExpect(jsonPath("$[1].id").value("e2"))
                .andExpect(jsonPath("$[1].description").value("Dinner"))
                .andExpect(jsonPath("$[1].amount").value(30.0));
    }

    @Test
    void getIncomesByGroup_returnsListOfIncomeDtos() throws Exception {
        String groupId = "g1";
        List<Income> incomes = List.of(
                createIncome("i1", "Work", 1000.0, "Salary", "2024-01-01", "user1", groupId),
                createIncome("i2", "Work", 200.0, "Bonus", "2024-01-02", "user1", groupId)
        );
        when(incomeService.getIncomesByGroup(groupId)).thenReturn(incomes);

        mockMvc.perform(get("/api/groups/{groupId}/incomes", groupId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("i1"))
                .andExpect(jsonPath("$[0].description").value("Salary"))
                .andExpect(jsonPath("$[0].amount").value(1000.0))
                .andExpect(jsonPath("$[1].id").value("i2"))
                .andExpect(jsonPath("$[1].description").value("Bonus"))
                .andExpect(jsonPath("$[1].amount").value(200.0));
    }

    @Test
    void deleteGroup_returnsNoContent() throws Exception {
        String groupId = "g1";
        doNothing().when(groupService).deleteGroup(groupId);

        mockMvc.perform(delete("/api/groups/delete")
                        .param("groupId", groupId))
                .andExpect(status().isNoContent());
    }
}
