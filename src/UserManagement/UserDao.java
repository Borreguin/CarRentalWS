package UserManagement;



import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static Connection conn;
    public List<User> getAllUsers(){
        // list of user to return
        List<User> userList = null;
        try {
            File file = new File("Users.dat");
            if (!file.exists()) {
                // if the document "Users.dat" does not exist
                User user = new User(1, "Mahesh", "Teacher");
                userList = new ArrayList<>();
                userList.add(user);
                userList.add(new User(2,"Roberto", "Master"));
                userList.add(new User(3,"Gonzalo", "Engineer"));
                saveUserList(userList);
            }
            else{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }


    // CRUD actions:
    // Gets an user from "Users.dat":
    public User getUser(int id){
        List<User> users = getAllUsers();
        // check the user
        for (User user: users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public int addUser(User pUser){
        List<User> users = getAllUsers();
        boolean userExits = false;
        for(User user: users){
            if(user.getId() == pUser.getId()){
                userExits = true;
                break;
            }
        }
        if(!userExits){
            users.add(pUser);
            saveUserList(users);
            return 1;
        }
        return 0;
    }

    public int updateUser(User pUser){
        List<User> users = getAllUsers();
        for(User user: users){
            int index = users.indexOf(user);
            users.set(index, pUser);
            saveUserList(users);
            return 1;
        }
        return 0;
    }

    public int deleteUser(int id){
        List<User> users = getAllUsers();
        for(User user: users){
            if(user.getId() == id){
                int index = users.indexOf(user);
                users.remove(index);
                saveUserList(users);
                return 1;
            }
        }
        return 0;
    }

    // End of the CRUD actions

    private void saveUserList(List<User> userList){
        try {
            File file = new File("Users.dat");
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
