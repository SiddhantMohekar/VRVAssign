package vrv.vrvassign.Controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vrv.vrvassign.Model.Role;
import vrv.vrvassign.Model.User;
import vrv.vrvassign.Repos.UserRepository;
import vrv.vrvassign.Security.SecurityService;

@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/showReg")
    public String getRegister(){
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(2l);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return "login";
    }

    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String email,String password,HttpServletRequest request,HttpServletResponse response){
        boolean loginResponse = securityService.login(email, password,request,response);
        if (loginResponse) {
            return "index";
        }
        return "login";
    }

    
}
