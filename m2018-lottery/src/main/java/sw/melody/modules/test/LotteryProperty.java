package sw.melody.modules.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Setter
@Getter
@Component
public class LotteryProperty {

    @Value("${sw.melody.project.name}")
    private String name;

    @Value("${sw.melody.project.title}")
    private String title;

    @Value("${sw.melody.project.desc}")
    private String desc;

    @Value("${sw.melody.project.value}")
    private String value;
    @Value("${sw.melody.project.number}")
    private Integer number;
    @Value("${sw.melody.project.bignumber}")
    private Long bignumber;
    @Value("${sw.melody.project.test1}")
    private Integer test1;
    @Value("${sw.melody.project.test2}")
    private Integer test2;
}
