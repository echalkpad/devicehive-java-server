package com.devicehive.dao.riak;

import com.devicehive.dao.*;
import com.devicehive.model.*;
import com.devicehive.model.enums.AccessKeyType;
import com.devicehive.model.enums.UserRole;
import com.devicehive.model.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Profile("riak")
@Component
public class InitialDataLoader {
    @Autowired
    DeviceClassDao deviceClassDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AccessKeyDao accessKeyDao;

    @Autowired
    AccessKeyPermissionDao accessKeyPermissionDao;

    @Autowired
    ConfigurationDao configurationDao;

    @Autowired
    NetworkDao networkDao;

    @Autowired
    DeviceDao deviceDao;

    public void initialData () {

        User user = new User();
        user.setId(2L);
        user.setLogin("test_admin");
        user.setPasswordHash("+IC4w+NeByiymEWlI5H1xbtNe4YKmPlLRZ7j3xaireg=");
        user.setPasswordSalt("9KynX3ShWnFym4y8Dla039py");
        user.setRole(UserRole.ADMIN);
        user.setStatus(UserStatus.ACTIVE);
        user.setLoginAttempts(0);
        user.setEntityVersion(0);
        userDao.persist(user);

        AccessKey key = new AccessKey();
        key.setId(1L);
        key.setLabel("Access Key for dhadmin");
        key.setKey("1jwKgLYi/CdfBTI9KByfYxwyQ6HUIEfnGSgakdpFjgk=");
        key.setExpirationDate(null);
        key.setUser(user);
        key.setEntityVersion(1);
        if (key.getPermissions() == null) key.setPermissions(new HashSet<>());
        AccessKeyPermission permission = new AccessKeyPermission();
        permission.setAccessKey(key);
        permission.setEntityVersion(1L);
        key.getPermissions().add(permission);
        accessKeyDao.persist(key);

        Configuration cfg;
        cfg = new Configuration("google.identity.allowed", "true");
        configurationDao.persist(cfg);
        cfg = new Configuration("google.identity.client.id", "google_id");
        configurationDao.persist(cfg);
        cfg = new Configuration("facebook.identity.allowed", "true");
        configurationDao.persist(cfg);
        cfg = new Configuration("facebook.identity.client.id", "facebook_id");
        configurationDao.persist(cfg);
        cfg = new Configuration("github.identity.allowed", "true");
        configurationDao.persist(cfg);
        cfg = new Configuration("github.identity.client.id", "github_id");
        configurationDao.persist(cfg);
        cfg = new Configuration("session.timeout", "1200000");
        configurationDao.persist(cfg);
        cfg = new Configuration("allowNetworkAutoCreate", "true");
        configurationDao.persist(cfg);

        // -- 2. Default device classes
        //INSERT INTO device_class (name, is_permanent, offline_timeout) VALUES ('Sample VirtualLed Device', FALSE, 600);

        DeviceClass deviceClass = new DeviceClass();
        deviceClass.setId(1L);
        deviceClass.setName("Sample VirtualLed Device");
        deviceClass.setPermanent(false);
        deviceClass.setOfflineTimeout(600);
        deviceClassDao.persist(deviceClass);
        //INSERT INTO network (name, description) VALUES ('VirtualLed Sample Network', 'A DeviceHive network for VirtualLed sample');

        Network network = new Network();
        network.setId(1L);
        network.setName("VirtualLed Sample Network");
        network.setDescription("A DeviceHive network for VirtualLed sample");
        networkDao.persist(network);

        //INSERT INTO device (guid, name, status, network_id, device_class_id, entity_version) VALUES
        // ('E50D6085-2ABA-48E9-B1C3-73C673E414BE', 'Sample VirtualLed Device', 'Offline', 1, 1, 1);
        Device device = new Device();
        device.setId(1L);
        device.setGuid("E50D6085-2ABA-48E9-B1C3-73C673E414BE");
        device.setName("Sample VirtualLed Device");
        device.setStatus("Offline");
        device.setNetwork(network);
        device.setDeviceClass(deviceClass);
        device.setEntityVersion(1);
        deviceDao.persist(device);

        //live data
        User user2 = new User();
        user2.setId(1L);
        user2.setLogin("dhadmin");
        user2.setPasswordHash("DFXFrZ8VQIkOYECScBbBwsYinj+o8IlaLsRQ81wO+l8=");
        user2.setPasswordSalt("sjQbZgcCmFxqTV4CCmGwpIHO");
        user2.setRole(UserRole.ADMIN);
        user2.setStatus(UserStatus.ACTIVE);
        user2.setLoginAttempts(0);
        user2.setEntityVersion(0);
        userDao.persist(user2);

        key = new AccessKey();
        key.setId(2L);
        key.setLabel("Access Key for dhadmin");
        key.setKey("1jwKgLYi/CdfBTI9KByfYxwyQ6HUIEfnGSgakdpFjgk=");
        key.setExpirationDate(null);
        key.setUser(user2);
        key.setType(AccessKeyType.DEFAULT);
        key.setEntityVersion(1);
        if (key.getPermissions() == null) key.setPermissions(new HashSet<>());
        AccessKeyPermission permission2 = new AccessKeyPermission();
        permission.setAccessKey(key);
        permission.setEntityVersion(1L);
        key.getPermissions().add(permission2);
        accessKeyDao.persist(key);

        cfg = new Configuration("websocket.ping.timeout", "120000");
        configurationDao.persist(cfg);

        cfg = new Configuration("cassandra.rest.endpoint", "http://127.0.0.1:8080/cassandra");
        configurationDao.persist(cfg);

        cfg = new Configuration("user.login.lastTimeout", "1000");
        configurationDao.persist(cfg);
    }


}