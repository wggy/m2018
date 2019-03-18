package com.wggy.prune.book.model;

import lombok.Data;

import javax.persistence.*;
import java.util.concurrent.*;

/**
 * @author ping
 * @create 2019-02-22 15:06
 **/
@Data
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reader;
    private String isbn;
    private String title;
    private String author;
    private String description;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.SECONDS, queue);

        Future<String> future = poolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "hello";
            }
        });
        String result = future.get();
        System.out.println(result);
    }
}
