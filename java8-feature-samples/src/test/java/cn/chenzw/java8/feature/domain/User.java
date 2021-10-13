package cn.chenzw.java8.feature.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private String name;

    private Date birthDate;

}
