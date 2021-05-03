package pers.clare.demo2.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(100) default ''")
    private String name;

    @Column(nullable = false, updatable = false, columnDefinition = "bigint default '0'")
    private Long createTime;
}
