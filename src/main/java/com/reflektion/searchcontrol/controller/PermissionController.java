package com.reflektion.searchcontrol.controller;


import com.reflektion.searchcontrol.model.*;
import com.reflektion.searchcontrol.service.PermissionService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * @author sebastiangroh@reflektion.com
 */
@Controller
@RequestMapping(value = "/permission", produces = {APPLICATION_JSON_VALUE})
@Api(value = "/permission", description = "the permission API")
public class PermissionController {

    PermissionService permissionService;

    @Inject
    public PermissionController(PermissionService permissionService) {
       this.permissionService = permissionService;
    }

    @ApiOperation(value = "Permission GET", notes = "It returns all the user permissions. ", response = Permission.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A set of permission objects, It should be a List of the Model specified", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = String.class)})
    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Set<Permission>> getPermissions(
            @ApiParam(value = "Roles Identifier.", required = false) @RequestParam(value = "roleId", required = false) List<Long> roleIds)
            throws NotFoundException {
        return new ResponseEntity(permissionService.getPermission(roleIds), HttpStatus.OK);
    }


    @ApiOperation(value = "Permission POST", notes = "It creates a new permission (not based in user's context)", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The permission has been successfully created", response = Long.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Integer.class)})
    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Long> createPermission(
            @ApiParam(value = "Permission.", required = true) @RequestBody PermissionDTO permission
    )
            throws NotFoundException {
        return new ResponseEntity(permissionService.addPermission(permission), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit a Key permission PUT", notes = "Updates a given permission", response = Permission.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully updated", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{permissionId}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Permission> updatePermissionByPermId(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") Long userId,
            @ApiParam(value = "Permission id.", required = true) @PathVariable("permissionId") Long permissionId,
            @ApiParam(value = "Permission", required = true) @RequestBody PermissionDTO permission
    )
            throws NotFoundException {
        return new ResponseEntity(permissionService.updatePermission(permissionId, permission), HttpStatus.OK);
    }


    @ApiOperation(value = "Permission by key name GET", notes = "It returns all the user permissions by key id. ", response = Permission.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A set of permission objects, It should be a List of the Model specified", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = String.class)})
    @RequestMapping(value = "/{keyId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Set<Permission>> getPermissionsByKeyName(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") String userId,
            @ApiParam(value = "Key id.", required = true) @PathVariable("keyId") Long keyId,
            @ApiParam(value = "Actions. (i.e: r, w, x)", required = false) @RequestParam(value = "action", required = false) List<String> action)
            throws NotFoundException {
        return new ResponseEntity(permissionService.getPermissionByKey(keyId), HttpStatus.OK);
    }

    @ApiOperation(value = "Permission by key id POST", notes = "Adds permission to some key id based on user's permissions.", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The permission has been successfully created", response = Long.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Long.class)})
    @RequestMapping(value = "/{keyId}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Long> createPermissionbyKeyName(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") Long userId,
            @ApiParam(value = "Key id.", required = true) @PathVariable Long keyId,
            @ApiParam(value = "Permission.", required = true) @RequestBody PermissionDTO permission)
            throws NotFoundException {
        return new ResponseEntity(permissionService.addPermissionKey(keyId, permission), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit a Key permission PUT", notes = "Updates a given permission on the key id specified", response = Permission.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully updated", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{permissionId}/key/{keyId}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Permission> updatePermissionByKeyName(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") Long userId,
            @ApiParam(value = "Permission id.", required = true) @PathVariable("permissionId") Long currentPermissionId,
            @ApiParam(value = "Key id.", required = true) @PathVariable("keyId") Long keyId,
            @ApiParam(value = "New Permission id", required = true) @RequestBody Long newPermissionId
    )
            throws NotFoundException {
        return new ResponseEntity(permissionService.updatePermissionKey(keyId, currentPermissionId, newPermissionId), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Key permission", notes = "Delete a given permission on the key id specified", response = Permission.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully updated", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{permissionId}/key/{keyId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity<Permission> deletePermissionByKeyName(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") Long userId,
            @ApiParam(value = "Permission id.", required = true) @PathVariable("permissionId") Long permissionId,
            @ApiParam(value = "Key id.", required = true) @PathVariable("keyId") Long keyId
    )
            throws NotFoundException {
        return new ResponseEntity(permissionService.deletePermissionKey(keyId, permissionId), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete all permission that a key has DELETE", notes = "Deletes all given permission on the key id specified. ", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The permission has been successfully deleted", response = Permission.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = Message.class)})
    @RequestMapping(value = "/{keyId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity deletePermissionByKeyName(
            @ApiParam(value = "User Identifier or token (depends of the tech decision).", required = true) @RequestHeader("userId") Long userId,
            @ApiParam(value = "Key id.", required = true) @RequestParam("keyId") Long keyId
    )
            throws NotFoundException {
        return new ResponseEntity(permissionService.deletePermissionsKey(keyId, userId), HttpStatus.OK);
    }
}
