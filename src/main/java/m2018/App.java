package m2018;

/**
 * Hello world!
 *
 * …or create a new repository on the command line

 git提交方式

 1.从本地仓库提交
 创建本地仓库
 git init
 提交本地分支到暂存区
 git add README.md
 提交本地分支
 git commit -m "first commit"
 本地分支关联github远程仓库
 git remote add origin https://github.com/year201412/m2018.git
 提交到github仓库
 git push -u origin master

 2.从本地仓库提交到已知github仓库
 …or push an existing repository from the command line

 关联githu远程仓库
 git remote add origin https://github.com/year201412/m2018.git
 提交到github仓库
 git push -u origin master
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
