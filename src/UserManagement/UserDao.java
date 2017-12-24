package UserManagement;



import classes.Car;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static Connection conn;
    public List<Car> getAllUsers(){
        // list of car to return
        List<Car> carList = null;

        Car car = new Car(1, "Mahesh", "Teacher");
        carList = new ArrayList<>();
        carList.add(car);
        carList.add(new Car(2,"Roberto", "Master"));
        carList.add(new Car(3,"Gonzalo", "Engineer"));
        /*try {
            File file = new File("Users.dat");
            if (!file.exists()) {
                // if the document "Users.dat" does not exist
                Car car = new Car(1, "Mahesh", "Teacher");
                carList = new ArrayList<>();
                carList.add(car);
                carList.add(new Car(2,"Roberto", "Master"));
                carList.add(new Car(3,"Gonzalo", "Engineer"));
                saveUserList(carList);
            }
            else{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                carList = (List<Car>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return carList;
    }


    // CRUD actions:
    // Gets an user from "Users.dat":
    public Car getUser(int id){
        List<Car> cars = getAllUsers();
        // check the user
        for (Car car : cars){
            if(car.getId() == id){
                return car;
            }
        }
        return null;
    }

    public int addUser(Car pCar){
        List<Car> cars = getAllUsers();
        boolean userExits = false;
        for(Car car : cars){
            if(car.getId() == pCar.getId()){
                userExits = true;
                break;
            }
        }
        if(!userExits){
            cars.add(pCar);
            saveUserList(cars);
            return 1;
        }
        return 0;
    }

    public int updateUser(Car pCar){
        List<Car> cars = getAllUsers();
        for(Car car : cars){
            int index = cars.indexOf(car);
            cars.set(index, pCar);
            saveUserList(cars);
            return 1;
        }
        return 0;
    }

    public int deleteUser(int id){
        List<Car> cars = getAllUsers();
        for(Car car : cars){
            if(car.getId() == id){
                int index = cars.indexOf(car);
                cars.remove(index);
                saveUserList(cars);
                return 1;
            }
        }
        return 0;
    }

    // End of the CRUD actions

    private void saveUserList(List<Car> carList){
        try {
            File file = new File("Users.dat");
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(carList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
