package sw.melody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 * <p>
 * …or create a new repository on the command line
 * <p>
 * git提交方式
 * <p>
 * 1.从本地仓库提交
 * 创建本地仓库
 * git init
 * 提交本地分支到暂存区
 * git add README.md
 * 提交本地分支
 * git commit -m "first commit"
 * 本地分支关联github远程仓库
 * git remote add origin https://github.com/year201412/m2018.git
 * 提交到github仓库
 * git push -u origin master
 * <p>
 * 2.从本地仓库提交到已知github仓库
 * …or push an existing repository from the command line
 * <p>
 * 关联github远程仓库
 * git remote add origin https://github.com/year201412/m2018.git
 * 提交到github仓库
 * git push -u origin master
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
