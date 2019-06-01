package site.aboy.album.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.aboy.album.entity.Admin;
import site.aboy.album.repository.AdminRepository;
import site.aboy.album.utils.Util;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;

    public String adminLoginAction(String username,String password){
        Admin admin = adminRepository.findAdminByUsername(username);
        if (admin.getPassword().equals(password)){
            return Util.FormatString(200, "login success");
        }
        return Util.FormatString(400, "invalid account or password");
    }
}
