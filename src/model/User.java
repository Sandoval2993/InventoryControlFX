package model;

import dao.UserDAO;
import dto.UserDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class User {
public LinkedHashMap getUserMap(){
    LinkedHashMap userMap = new LinkedHashMap();
    try {
        UserDAO userDAO = new UserDAO();
        ArrayList<UserDTO> listGroups = (ArrayList<UserDTO>) userDAO.readAll();
        listGroups.forEach(userDTO -> {
            userMap.put(userDTO.getUserName(), userDTO);
        });
    }catch (Exception ex){
        userMap.put(0,0);
    }
    return userMap;
}
}
