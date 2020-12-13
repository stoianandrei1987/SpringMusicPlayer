package ro.andreistoian.SpringMusicPlayer.repository;


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ro.andreistoian.SpringMusicPlayer.models.Role;
import ro.andreistoian.SpringMusicPlayer.models.User;

import java.util.Set;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEmployeeRepository(){
        Role role = roleRepository.findRoleByRoleName("USER");
        Assert.assertEquals("USER", role.getRoleName());
    }

    @Test
    public void testUserRepository(){
        User found = userRepository.findByUserName("gigishor").get();
        Assertions.assertEquals(55, found.getAge());
    }

    @Test
    public void testIfRoleForUserIs()
    {
        User found = userRepository.findByUserName("andrei").get();
        Set<Role> roles = found.getRoles();
        Assert.assertEquals(2, roles.size());
        Assert.assertTrue(roles.contains(roleRepository.findRoleByRoleName("USER")));
    }

}