package com.app.management.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.ExpenseDto;
import com.app.management.bean.GroupDto;
import com.app.management.bean.IncomeDto;
import com.app.management.mapper.ExpenseMapper;
import com.app.management.mapper.GroupMapper;
import com.app.management.mapper.IncomeMapper;
import com.app.management.service.ExpenseService;
import com.app.management.service.GroupService;
import com.app.management.service.IncomeService;
import com.app.management.service.exceptions.InstanceNotFoundException;
import com.app.management.service.exceptions.InvalidParameterException;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupService groupService;

    private ExpenseService expenseService;

    private IncomeService incomeService;

    public GroupController(GroupService groupService, ExpenseService expenseService, IncomeService incomeService) {
        this.groupService = groupService;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @PostMapping("/createGroup")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(GroupMapper.toGroupDto(groupService.createGroup(GroupMapper.toGroup(groupDto))));        
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups().stream().map(GroupMapper::toGroupDto).toList());
    }

    @GetMapping("/myGroups")
    public ResponseEntity<List<GroupDto>> getUserGroups(){
        return ResponseEntity.ok(groupService.getUserGroups().stream().map(GroupMapper::toGroupDto).toList());
    }

    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<List<ExpenseDto>> getExpensesByGroup(@PathVariable String groupId){
        return ResponseEntity.ok(expenseService.getExpensesByGroup(groupId).stream().map(ExpenseMapper::toExpenseDto).toList());
    }

    @GetMapping("/{groupId}/incomes")
    public ResponseEntity<List<IncomeDto>> getIncomesByGroup(@PathVariable String groupId){
        return ResponseEntity.ok(incomeService.getIncomesByGroup(groupId).stream().map(IncomeMapper::toIncomeDto).toList());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGroup(@RequestParam String groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable String groupId) {
        try {
            groupService.removeUserFromGroup(groupId);
            return ResponseEntity.noContent().build();
        } catch (InstanceNotFoundException | InvalidParameterException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
