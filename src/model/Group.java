package model;

import dao.GroupDAO;
import dto.GroupDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Group {
    private GroupDAO groupDAO;

    public Group(){
    }

    public ObservableList getUpdateNameGroupList(){
        ObservableList<String> updateNameGroupList = FXCollections.observableArrayList();
        try {
            groupDAO = new GroupDAO();
            ArrayList<GroupDTO> listGroups = (ArrayList<GroupDTO>) groupDAO.readAll();

            Iterator iteratorGroups = listGroups.iterator();
            while (iteratorGroups.hasNext()) {
                GroupDTO groupDTO = (GroupDTO) iteratorGroups.next();
                updateNameGroupList.add(groupDTO.getGroupName());
            }
            return updateNameGroupList;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return updateNameGroupList;
    }

    public int searchIdGroup(String nameGroup) throws SQLException {
       groupDAO = new GroupDAO();
       int idGroup = 0;

       ArrayList<GroupDTO> listGroup = (ArrayList<GroupDTO>) groupDAO.readAll();

       Iterator iteratorGroups = listGroup.iterator();

       while (iteratorGroups.hasNext()){
           GroupDTO groupDTO = (GroupDTO) iteratorGroups.next();
           if (nameGroup.equals(groupDTO.getGroupName())){
               idGroup = groupDTO.getGroupId();
               break;
           }
       }
        return idGroup;
    }
}
