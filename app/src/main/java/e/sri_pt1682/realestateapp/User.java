package e.sri_pt1682.realestateapp;

import java.util.UUID;

/**
 * Created by sri-pt1682 on 28/02/18.
 */

public class User
{
    String name,id,username,password,photo;
    long phno;

    public User(String name, String username, String password, long phno)
    {
        this.name = name;
        id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.phno = phno;
        photo=String.valueOf(R.drawable.ic_launcher_foreground);
    }

    public User(String name, String id, String username, String password, String photo, long phno) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.phno = phno;
    }

    public User() {
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhno(long phno) {
        this.phno = phno;
    }

    public String getPhoto() {

        return photo;
    }

    public User(String name, String id, String username, String password, long phno) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.phno = phno;
        photo=String.valueOf(R.drawable.ic_launcher_foreground);

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getPhno() {
        return phno;
    }
}
