package model;

import dao.GroupDAO;
import dto.GroupDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.*;

public class Group {
    private GroupDAO groupDAO;

    public Group() {
    }

    public LinkedHashMap getGroupMap(){
        LinkedHashMap groupMap = new LinkedHashMap();
        try {
            groupDAO = new GroupDAO();
            ArrayList<GroupDTO> listGroups = (ArrayList<GroupDTO>) groupDAO.readAll();
            listGroups.forEach(groupDTO -> {
                groupMap.put(groupDTO.getGroupName(), groupDTO.getGroupId());
            });
        }catch (Exception ex){
            groupMap.put(0,0);
        }
        return groupMap;
    }
}
