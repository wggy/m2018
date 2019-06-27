package com.wggy.prune.sync.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author wggy
 * @create 2019-06-26 17:38
 **/
@Data
@Entity
@Table(name = "tbl_dictionary")
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message="字典键不能为空")
    private String dictKey;

    @Column(nullable = false)
    @NotBlank(message="字典取值不能为空")
    private String dictValue;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Date lastUpdateTime;
}
