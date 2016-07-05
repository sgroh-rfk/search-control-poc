package com.reflektion.searchcontrol.controller;

import com.google.common.collect.Lists;
import com.reflektion.searchcontrol.model.Message;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionDTO;
import com.reflektion.searchcontrol.model.permission.Role;
import com.reflektion.searchcontrol.service.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author sebastiangroh@reflektion.com
 */
@Controller
@RequestMapping(value = "/role", produces = {APPLICATION_JSON_VALUE})
public class RoleController {

    RoleService roleService;

    @Inject
    public void RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @ApiOperation(value = "Permission by role GET", notes = "It returns all the user permissions by role.", response = Permission.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A set of permission objects, It should be a List of the Model specified", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = String.class)})
    @RequestMapping(value = "/{roleId}/permission",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Set<Permission>> getPermissionsByRole(
            @ApiParam(value = "Actions.", required = false) @RequestParam(value = "action", required = false) List<String> action,
            @ApiParam(value = "Role Id.", required = true) @PathVariable("roleId") Long roleId)
            throws NotFoundException {

        return new ResponseEntity(roleService.getPermissionByRole(roleId), HttpStatus.OK);
    }

    @ApiOperation(value = "Permission by role POST", notes = "Adds a permission to some role.", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The permission has been successfully created", response = Long.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Integer.class)})
    @RequestMapping(value = "/{roleId}/permission",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Boolean> createPermissionbyRole(
            @ApiParam(value = "Permission id.", required = true) @RequestBody Long permissionId,
            @ApiParam(value = "Role id.", required = true) @RequestParam("roleId") Long roleId)
            throws NotFoundException {
        return new ResponseEntity(roleService.addPermissiontoRole(roleId, permissionId ), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Permission by role PUT", notes = "Updates a given permission on the role specified. ", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully updated", response = PermissionDTO.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{roleId}/permission",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Permission> updatePermissionByRole(
            @ApiParam(value = "Permission", required = true) @RequestBody PermissionDTO permission,
            @ApiParam(value = "Role id.", required = true) @RequestParam("roleId") Long roleId
    )
            throws NotFoundException {
        return new ResponseEntity(roleService.updatePermissiontoRole(permission, roleId), HttpStatus.OK);
    }

    @ApiOperation(value = "Permission by role DELETE", notes = "Deletes a given permission on the role specified. ", response = Permission.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully deleted", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{roleId}/permission/{permissionId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity deletePermissionByRole(
            @ApiParam(value = "Role id.", required = true) @PathVariable("roleId") Long roleId,
            @ApiParam(value = "Permission id.", required = true) @PathVariable("permissionId") Long permissionId
    )
            throws NotFoundException {
        return new ResponseEntity(roleService.deletePermissionRole(permissionId, roleId), HttpStatus.OK);
    }
}
