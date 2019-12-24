package socketsapp.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private String message;
    private String token;
    private String role;

    private User() {
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';

    }
    public static Builder newBuilder(){
        return new User().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(Long id) {
            User.this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder setMessage(String message) {
            User.this.message = message;
            return this;
        }

        public Builder setToken(String token) {
            User.this.token = token;
            return this;
        }

        public Builder setRole(String role) {
            User.this.role = role;
            return this;
        }

        public User build() {
            return User.this;
        }
    }


}
