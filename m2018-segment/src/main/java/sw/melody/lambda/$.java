package sw.melody.lambda;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ping
 * @create 2019-03-22 11:35
 **/

public class $ {
    public static void main(String[] args) {
//        Runnable r = () -> System.out.println("r .....");
//        ActionListener al = event -> System.out.println("click");
//        Runnable runnable = () -> {
//            System.out.println("runnable ...");
//        };
//        BinaryOperator<Long> add = (x, y) -> x+y;
//        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
//
//        Predicate<Integer> flag =  x -> x > 5;

        List<String> list = Stream.of("Shanghai", "Beijing", "Hongkong", "London", "Paris").collect(Collectors.toList());

        List upperList = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        List<Integer> mergeList = Stream.of(Arrays.asList(1, 2, 4, 5), Arrays.asList(3, 4, 4)).flatMap(member -> member.stream().distinct()).collect(Collectors.toList());
        int min = mergeList.stream().reduce((r, ele) -> r+=ele).get();


        mergeList.stream().collect(Collectors.partitioningBy(artist -> artist>5));
        Optional emptyOptional = Optional.empty();
        Optional alsoEmpty = Optional.ofNullable(null);
        System.out.println(emptyOptional);
        System.out.println(alsoEmpty);
        System.out.println(list);
        System.out.println(upperList);
        System.out.println(mergeList);
        System.out.println(min);
    }
}
