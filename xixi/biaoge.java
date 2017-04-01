package xixi;

public class biaoge {

	
	/*
	 *  Student(S#,Sname,Sage,Ssex) 学生表 
		Course(C#,Cname,T#) 课程表 
		SC(S#,C#,score) 成绩表 
		Teacher(T#,Tname) 教师表
	*/

	
	// 1.查询“001”课程比“002”课程成绩高的所有学生的学号； 
	
	//第一次思路： 1，查询已知课程名的成绩  2，比较成绩
	
	select score from SC 
	group by C# having C#= (select Cname from Course where Cname in('001','002'));
	
	
	/*
	 *   思路：1 根据2个课程编号 分别查询 学号   2 查询 1课 比2课 高的学号
	 *   疑问点： 查询的表格不是SC 为什么可以用条件 score 和 s# 啊？
* select a.S# from (select S# from SC where C# ='001') a ,(select S# from SC where C# ='002') b 
	   where a.score > b.score and a.s# = b.S#;
	*/
	
	
	//2、查询平均成绩大于60分的同学的学号和平均成绩；
	
	//思路： 1 查询平均成绩  查询出学生  2 查询学生的平均成绩   
	//什么时候 avg(score)是不用球的？
	select avg(score),s# from SC  
	where s# = a.S# 
	group by S# having (select avg(score) from SC ) > 60;
	
	
	//select S# ,avg(score) from sc group by S# having avg(score) > 60;
	
	
	
	
	//3、查询所有同学的学号、姓名、选课数、总成绩； 
	
	select S#,Sname, (select count(*) from Course group by C#) , sum(score) from SC ;
	
	/*
	 select Student.S#,Student.Sname,count(SC.C#),sum(score) from Student left Outer join SC 
	 on  Student.S# = SC.S# group by Student.S#,Sname;
	*/
	
	
	//4、查询姓“李”的老师的个数；
	
	//错误：count(Tname)不知道这样是可以数名字的，同时，使用模糊查询时候一定要用like
	select count(*) from Teacher where Tname = '李%';
	
	//select count(distinct(Tname)) from Teacher where Tname like '李%';
	
	
	//5、查询没学过“叶平”老师课的同学的学号、姓名；
	
	//思路： 1 查询 叶萍老师的课的学号   2 查询学生信息
	
	select T# from Teacher where Tname = '叶萍';
	select c# from Course where Course.T# = Teacher.T#; 
	select s# from SC where SC.S# = Course.C#;
	select student.S#,Sname from Student where  student.S# = SC.S#;
	
	/*
	 *  就没有想到   条件是可以直接用 and 联系一起用的（- -自己先分开一句句写的 当然没想到了）
	 
	  student.S# ,Student.Sname from student where S# not in
	(select distinct(SC.S#) from  sc,Course,Teacher where 
			SC.C# = Course.C# and Teacher.T#= Course.T# and Teacher.Tname = '叶萍' );
	*/
	
	
	//6、查询学过“001”并且也学过编号“002”课程的同学的学号、姓名；
	
	//思路： 1 同时学了两门课程的学号   2 查询学号信息
	//疑难点： 我不知道 怎么查询同时选择两门课的学号
	select Student.S#,Student.Sname from Student  where S# in 
	(select SC.S# from  SC where sc.C#  in('001','002'));

	/*
	 * 怎么查询同时选择两门课的学号
	 * Student.S# = SC.S# and SC.C# = '001' and exists (select * from SC as sc_2 
	 * where SC_2.S# = SC.S# and SC_2.C# = '002')
	  

	  select Student.s#,Student.Sname from Student,SC where Student.S# = SC.S# and SC.C# = '001' 
			and exists (select * from SC as sc_2 where SC_2.S# = SC.S# and SC_2.C# = '002');
	*/
	
	
	
	//7、查询学过“叶平”老师所教的所有课的同学的学号、姓名；
	
	//思路： 1 查询 老师所教的所有课   2查询所有课里面所有的学生  3 查询学生信息
	
	select T# from Teacher where Tname = '叶平';
	
	select S# from SC ,Course where sc.c# = Course.C# and Course.T# = Teacher.T#;
	
	select Student.s#,Student.Sname from Student where Student.S# = SC.S#;
	
	
	select Student.s#,Student.Sname from Student where S# in 
	(select SC.S# from SC,Course,Tname 
			where sc.c# = Course.C# and Course.T# = Teacher.T# and Teacher.Tname ='叶平');
	
	/*
	 * 没有想到的是  2查询所有课里面所有的学生  ==》怎么查询所有课的所有的学生
	 * 
	 * 查询所有课：select count(C#) from  Course,Teacher 
	                   where Teacher.T# = Course.T# and Tname = '叶平'
	 * 
	 * 查询所有学生：(select S# from SC ,Course ,Teacher 
	 * where SC.C#=Course.C# and Teacher.T#=Course.T# and Teacher.Tname='叶平'
		group by S# having count(SC.C#) = 
		(select count(C#) from  Course,Teacher where Teacher.T# = Course.T# and Tname = '叶平');
	
	   所有课 和所有学生 这两个变量  都需要条件？  可能吧
	  
	 select S#,Sname from Student  where S# in 
	(select S# from SC ,Course ,Teacher 
	where SC.C#=Course.C# and Teacher.T#=Course.T# and Teacher.Tname='叶平'
	group by S# having count(SC.C#) = 
	(select count(C#) from  Course,Teacher where Teacher.T# = Course.T# and Tname = '叶平');
	*/
	
	
	//8、查询课程编号“002”的成绩比课程编号“001”课程低的所有同学的学号、姓名；
	
	//思路： 1查询 001 和002 课程的学号  2 查询学生信息(条件是学号相同,同时比较分数大小 )
	
    select  b.S# from (select s# from SC where c# = '001') as a,
	   (select s# from sc where C# = '002') as b
		where a.score < b.score and a.S# = b.S#;
		select * from student where Student.S# = b.S#;
		
	//看不懂
	select S#,Sname from (select sTudent.S#,student.sname,score,(select score from SC SC_2) 
			where SC_2.S# = Student.S# and SC_2.C#= '002') score2 
    from Student ,SC where Student.s# = SC.S# and c# = '001') s_2 where score2 < score;
	
	
	//9、查询所有课程成绩小于60分的同学的学号、姓名；
	//思路： 1 先查询同学的学号和姓名    2 查询所有课程的成绩《60 的学号 
	//难点：用了两张表，所以中间用学号Snum来连接
	SELECT  s.Snum,s.Sname from student as s,sc where sc.Snum = s.Snum AND sc.Snum in
			(SELECT Snum from sc  where score < 60);

	/*
	 select s.Snum,s.Sname from student as s WHERE s.Snum NOT in 
	(SELECT s2.Snum from student as s2,sc where s2.Snum= sc.Snum AND score < 60);
	*/
	
	
	
	//10、查询没有学全所有课的同学的学号、姓名；
	//思路： 1查询所有课程的数量    2 查询学完所有课程的学号 然后not in  3 查询 同学的学号 和姓名
	
	SELECT Snum,Sname from student as s where s.Snum not  in 
	(SELECT Snum from sc GROUP BY Snum HAVING count(Cnum) = 5);
	
	SELECT Snum,Sname from student as s where s.Snum not  in 
	(SELECT Snum from sc GROUP BY Snum HAVING count(Cnum) = (SELECT count(Cnum) from course));
	
	SELECT s.Snum ,s.Sname from student as s ,sc where s.Snum = sc.Snum
			GROUP BY s.Snum,s.Sname HAVING count(Cnum) = (SELECT count(Cnum) from course);
	
	
	
	//11、查询至少有一门课与学号为“1001”的同学所学相同的同学的学号和姓名；
	//思路：1 查询‘001’同学所学课程的编号     2 查询学号，条件是课程编号在‘001’同学所学的课程编号里 
	//     3 查询 同学的学号和姓名
	
	SELECT s.sname,s.snum ,s.Sage from student as s where s.Snum in
	(SELECT Snum from sc where Cnum in (SELECT Cnum from sc  WHERE Snum = '1' ))
	
	SELECT s.sname,s.snum ,s.Sage from student as s where s.Snum in
	(SELECT Snum from sc  where Cnum in (SELECT Cnum from sc  WHERE Snum = '1' ))
	
	
	//12、查询至少学过学号为“001”同学所有一门课的其他同学学号和姓名；
	//思路： 1查询 学号为‘1’的学生所学的课程    2 根据课程查询 学生的信息
	
	SELECT s.Sname,s.Snum from student as s,sc where s.Snum = sc.Snum and sc.Cnum in
	(select s2.Cnum from sc as s2 where s2.Snum = '1');
	
	/*
	 select distinct SC.S#,Sname from Student,SC 
     where Student.S#=SC.S# and C# in (select C# from SC where S#='001'); 
    */

	
	//13、把“SC”表中“叶平”老师教的课的成绩都更改为此课程的平均成绩；
	//为什么不能查询？ 答案是错的？
	UPDATE  sc 
	set score = (SELECT avg(s2.score) from sc as s2 where s2.Cnum = sc.Cnum)
	from teacher,course  
	where course.Cnum = sc.Cnum and course.Tnum = teacher.Tnum and Tname = '赵红';
	
	UPDATE sc  SET score = 
			(SELECT avg(s2.score) from sc as s2 where Cnum = s2.Cnum)
		where Cnum in 
			(SELECT course.Cnum 
			 from course ,teacher
			 where course.Tnum = teacher.Tnum 
			 and Tname='李相赫');
	
	
	//14、查询和“1002”号的同学学习的课程完全相同的其他同学学号和姓名；
	/**
	 * 1   查询所有信息 学号‘2’的学生信息
	 * 2   分组的条件  =  1返回的变量
	 * 3   根据学号分组
	 * 4   查询的条件： 课程 完全 和‘2’的课程一样  （但是为什么一个 in 就可以了？）
	 * 5   查询学生信息
	 */
	SELECT Snum from sc where Cnum in (SELECT Cnum from sc where Snum = '2')
	GROUP BY Snum 
	HAVING count(*) = (SELECT count(*) from sc where Snum ='2');
	
	
	//15、删除学习“叶平”老师课的SC表记录；
	//难点： 在于 多表 联合删除
	//但是答案删除不了===》 答案又错了？
	
	delete  s1 from sc as s1 ,course,teacher 
	where course.Cnum = s1.Cnum and course.Tnum = teacher.Tnum and Tname ='李相赫';
	
 /*   Delect SC 
    from course ,Teacher  
    where Course.C#=SC.C# and Course.T#= Teacher.T# and Tname='叶平';
 */

	/**
	 * 正确的查询语句
	 * delete from sc where cnum in 
		(SELECT course.Cnum from course, teacher 
		where course.Tnum = teacher.Tnum and Tname = '赵红');
	 */
	
  //16、向SC表中插入一些记录，这些记录要求符合以下条件：
				//没有上过编号“003”课程的同学学号、
				//2、 号课的平均成绩
     
	INSERT sc SELECT Snum,'3',
	(SELECT avg(score) from sc where Cnum ='3')
	from student where Snum not in (SELECT Snum from sc where cnum = '3');
	
	
	
}	