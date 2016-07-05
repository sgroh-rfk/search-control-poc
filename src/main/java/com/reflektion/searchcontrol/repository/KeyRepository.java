package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface KeyRepository extends CrudRepository<Key, Long> {

    Key findById(Long id);

}
