package api;

public class AuthorDTO {
    String username;
    String avatar;

    //for checking expected results
    public AuthorDTO(String username) {
        this.username = username;
    }


    //for REST-assured
    public AuthorDTO(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }


}
