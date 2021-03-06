package classes;

/***
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 ***/
public class Client {
    /***
     * This class contains the information about the client
     * For the moment the following are provided:
     *  - membership
     *  - age
     *
     * Note: Others attributes like name, address, etc. are not considered because
     * they were not considered in the problem statement, even though they
     * could be added easily in this class.
     */

    private int id_client;
    private Boolean  membership;
    private int age;

    public Client(int id_client, Boolean membership, int age) {
        this.id_client = id_client;
        this.membership = membership;
        this.age = age;
    }

    public Client(Boolean membership, int age) {
        this.membership = membership;
        this.age = age;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public Boolean getMembership() {
        return membership;
    }

    public void setMembership(Boolean membership) {
        this.membership = membership;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
