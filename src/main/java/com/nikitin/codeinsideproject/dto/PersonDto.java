package com.nikitin.codeinsideproject.dto;

import com.nikitin.codeinsideproject.util.RoleEnum;
import com.nikitin.codeinsideproject.util.annonation.PasswordMatches;

import javax.validation.constraints.*;

@PasswordMatches
public class PersonDto {

    @Size(min = 2, max = 30, message = "Имя пользователя должно содержать от 2 до 30 символов")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    @NotEmpty(message = "Повторный пароль не может быть пустым")
    private String passwordConfirm;

    @NotEmpty(message = "Электронная почта не может быть пустой")
    @Email(message = "Неккоректная электроная почта")
    private String email;

    private RoleEnum role = RoleEnum.USER;

    @Min(value = 1, message = "Возраст должен быть больше 1")
    @Max(value = 100, message = "Возраст должен быть меньше 100")
    private int age;

    public PersonDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
