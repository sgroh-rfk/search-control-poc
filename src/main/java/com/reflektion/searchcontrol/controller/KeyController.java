package com.reflektion.searchcontrol.controller;

import com.google.common.base.Strings;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.Message;
import com.reflektion.searchcontrol.service.KeyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/key", produces = {APPLICATION_JSON_VALUE})
@Api(value = "/key", description = "the key API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
public class KeyController {

    KeyService keyService;

    private static long counter = 1;

    @Inject
    public KeyController(@Qualifier("keyServiceImpl")KeyService keyService) {
        this.keyService = keyService;
    }


    @ApiOperation(value = "Keys", notes = "It returns all the keys that a User can access.", response = Key.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "An array of keys.", response = Key.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Key.class) })
  @RequestMapping(value = "",
    produces = { "application/json" },
    method = RequestMethod.GET)
  public ResponseEntity<Set<Key>> getKeys(
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) String userId,
          @ApiParam(value = "List the permssions for the given user.") @RequestParam(value = "includePermissions", required = false) Boolean includePermissions,
          @ApiParam(value = "Filter by key parent.") @RequestParam(value = "parent", required = false) Long parent)
      throws NotFoundException {
    Set<Key> keys = keyService.getKeys(parent);
    return new ResponseEntity(keys, HttpStatus.OK);
  }


  @ApiOperation(value = "Key", notes = "It returns the Key for the given identifiers. ", response = Key.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "Key information for the given identifiers.", response = Key.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Key.class) })
  @RequestMapping(value = "/{keyId}",
    produces = { "application/json" },
    method = RequestMethod.GET)
  public ResponseEntity<Key> getKeyByKeyName(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) Long userId,
          @ApiParam(value = "List the permssions for the given user.") @RequestParam(value = "includePermissions", required = false) Boolean includePermissions)
      throws NotFoundException {
      Key key = keyService.getKeyByKeyId(keyId);
      if (key == null) {
        throw new NotFoundException(404, "Key not found");
      }
      return new ResponseEntity<List<Key>>(HttpStatus.OK).ok(key);
  }


  @ApiOperation(value = "Key", notes = "It updates a key. ", response = Message.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "The keyValue has been successfully updated", response = Message.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Message.class) })
  @RequestMapping(value = "/{keyId}",
    produces = { "application/json" }, 
    
    method = RequestMethod.PUT)
  public ResponseEntity<Key> updateKeyByKeyName(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) String userId,
          @ApiParam(value = "Key." ,required=true ) @RequestBody Key key)
          throws Exception {
    Key keyToUpdate = keyService.getKeyByKeyId(keyId);
    if (keyToUpdate == null) {
      throw new NotFoundException(404, "Key not found");
    }
    keyService.updateKey(keyId, key);
    return new ResponseEntity<List<Key>>(HttpStatus.OK).ok(key);
  }


  @ApiOperation(value = "KeyValues", notes = "It returns all the keyValues that are managed currently for a keyName. ", response = KeyValue.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "An array of keyValues.", response = KeyValue.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = KeyValue.class) })
  @RequestMapping(value = "/{keyId}/value",
    produces = { "application/json" },
    method = RequestMethod.GET)
  public ResponseEntity<Set<KeyValue>> getKeyValuesForKeyName(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) String userId,
          @ApiParam(value = "Filter by live KeyValues.") @RequestParam(value = "live", required = false) Boolean live
)
      throws NotFoundException {
      Key key = keyService.getKeyByKeyId(keyId);
      if (key == null) {
        throw new NotFoundException(404, "Key not found");
      }
      Set<KeyValue> values = keyService.getKeyValuesForKey(keyId, live);
      return new ResponseEntity(HttpStatus.OK).ok(values);
  }


  @ApiOperation(value = "KeyValue", notes = "It returns the keyValue for the given identifiers. ", response = KeyValue.class, responseContainer = "List")
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "KeyValue information for the given identifier.", response = KeyValue.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = KeyValue.class) })
  @RequestMapping(value = "/{keyId}/value/{keyValueId}",
    produces = { "application/json" }, 
    
    method = RequestMethod.GET)
  public ResponseEntity<KeyValue> getKeyValueForKeyNameAndKeyValueId(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "KeyValue identifier.",required=true ) @PathVariable("keyValueId") Long keyValueId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) String userId
)
          throws Exception {
      Key key = keyService.getKeyByKeyId(keyId);
      if (key == null) {
        throw new NotFoundException(404, "Key not found");
      }
      KeyValue keyValue = keyService.getKeyValuesForKeyIdAndKeyValueId(keyId, keyValueId);
      if (keyValue == null) {
        throw new NotFoundException(404, "KeyValue not found");
      }
      return new ResponseEntity<List<KeyValue>>(HttpStatus.OK).ok(keyValue);
  }


  @ApiOperation(value = "KeyValue", notes = "It updates a keyValue. ", response = Message.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "The keyValue has been successfully updated", response = Message.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Message.class) })
  @RequestMapping(value = "/{keyId}/value/{keyValueId}",
    produces = { "application/json" }, 
    
    method = RequestMethod.PUT)
  public ResponseEntity<KeyValue> keyKeyNameValueKeyValueIdPut(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) Long userId,
          @ApiParam(value = "KeyValue identifier.",required=true ) @PathVariable("keyValueId") Long keyValueId,
          @ApiParam(value = "KeyValue." ,required=true ) @RequestBody KeyValue keyValue
)
          throws Exception {
      //TODO: this should be in one service not here
      Key key = keyService.getKeyByKeyId(keyId);
      if (key == null) {
        throw new NotFoundException(404, "Key not found");
      }
      keyValue.setId(keyValueId);
      keyService.updateKeyValueForKey(keyId, keyValue);
      if (keyValue == null) {
        throw new NotFoundException(404, "KeyValue not found");
      }
      return new ResponseEntity<Message>(HttpStatus.OK).ok(keyValue);
  }


  @ApiOperation(value = "KeyValue", notes = "It creates a new keyValue. ", response = Message.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "The keyValue has been successfully created", response = Message.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Message.class) })
  @RequestMapping(value = "/{keyId}/value",
    produces = { "application/json" },
    method = RequestMethod.POST)
  public ResponseEntity<Message> keyKeyNameValuePost(
          @ApiParam(value = "Key identifier.",required=true ) @PathVariable("keyId") Long keyId,
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) Long userId,
          @ApiParam(value = "KeyValue." ,required=true ) @RequestBody KeyValue keyValue
)
          throws Exception {
      Key key = keyService.getKeyByKeyId(keyId);
      if (key == null) {
        throw new NotFoundException(404, "Key not found");
      }
      keyValue.setId(counter++);
      Long createdKeyValue = keyService.createKeyValueForKey(keyId, keyValue);
      return new ResponseEntity<Message>(HttpStatus.CREATED).ok(new Message(String.valueOf(createdKeyValue)));
  }


  @ApiOperation(value = "Key", notes = "It creates a new key. ", response = Message.class)
  @ApiResponses(value = { 
    @ApiResponse(code = 200, message = "The key has been successfully created", response = Message.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = Message.class) })
  @RequestMapping(value = "",
    produces = { "application/json" },
    method = RequestMethod.POST)
  public ResponseEntity<Long> keyPost(
          @ApiParam(value = "User identifier." ,required=true ) @RequestHeader(value="userId", required=true) Long userId,
          @ApiParam(value = "Key." ,required=true ) @RequestBody Key key
)
          throws Exception {
      if (Strings.isNullOrEmpty(key.getName())) {
        throw new Exception("The keyName can not be null");
      }
      Long keyId = keyService.createKey(key.getId(), key);
      ResponseEntity<Message> response = new ResponseEntity<Message>(HttpStatus.CREATED);
      return new ResponseEntity(keyId, HttpStatus.OK);
  }

}
