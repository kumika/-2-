package xixi;

public class biaoge {

	
	/*
	 *  Student(S#,Sname,Sage,Ssex) ѧ���� 
		Course(C#,Cname,T#) �γ̱� 
		SC(S#,C#,score) �ɼ��� 
		Teacher(T#,Tname) ��ʦ��
	*/

	
	// 1.��ѯ��001���γ̱ȡ�002���γ̳ɼ��ߵ�����ѧ����ѧ�ţ� 
	
	//��һ��˼·�� 1����ѯ��֪�γ����ĳɼ�  2���Ƚϳɼ�
	
	select score from SC 
	group by C# having C#= (select Cname from Course where Cname in('001','002'));
	
	
	/*
	 *   ˼·��1 ����2���γ̱�� �ֱ��ѯ ѧ��   2 ��ѯ 1�� ��2�� �ߵ�ѧ��
	 *   ���ʵ㣺 ��ѯ�ı����SC Ϊʲô���������� score �� s# ����
* select a.S# from (select S# from SC where C# ='001') a ,(select S# from SC where C# ='002') b 
	   where a.score > b.score and a.s# = b.S#;
	*/
	
	
	//2����ѯƽ���ɼ�����60�ֵ�ͬѧ��ѧ�ź�ƽ���ɼ���
	
	//˼·�� 1 ��ѯƽ���ɼ�  ��ѯ��ѧ��  2 ��ѯѧ����ƽ���ɼ�   
	//ʲôʱ�� avg(score)�ǲ�����ģ�
	select avg(score),s# from SC  
	where s# = a.S# 
	group by S# having (select avg(score) from SC ) > 60;
	
	
	//select S# ,avg(score) from sc group by S# having avg(score) > 60;
	
	
	
	
	//3����ѯ����ͬѧ��ѧ�š�������ѡ�������ܳɼ��� 
	
	select S#,Sname, (select count(*) from Course group by C#) , sum(score) from SC ;
	
	/*
	 select Student.S#,Student.Sname,count(SC.C#),sum(score) from Student left Outer join SC 
	 on  Student.S# = SC.S# group by Student.S#,Sname;
	*/
	
	
	//4����ѯ�ա������ʦ�ĸ�����
	
	//����count(Tname)��֪�������ǿ��������ֵģ�ͬʱ��ʹ��ģ����ѯʱ��һ��Ҫ��like
	select count(*) from Teacher where Tname = '��%';
	
	//select count(distinct(Tname)) from Teacher where Tname like '��%';
	
	
	//5����ѯûѧ����Ҷƽ����ʦ�ε�ͬѧ��ѧ�š�������
	
	//˼·�� 1 ��ѯ ҶƼ��ʦ�Ŀε�ѧ��   2 ��ѯѧ����Ϣ
	
	select T# from Teacher where Tname = 'ҶƼ';
	select c# from Course where Course.T# = Teacher.T#; 
	select s# from SC where SC.S# = Course.C#;
	select student.S#,Sname from Student where  student.S# = SC.S#;
	
	/*
	 *  ��û���뵽   �����ǿ���ֱ���� and ��ϵһ���õģ�- -�Լ��ȷֿ�һ���д�� ��Ȼû�뵽�ˣ�
	 
	  student.S# ,Student.Sname from student where S# not in
	(select distinct(SC.S#) from  sc,Course,Teacher where 
			SC.C# = Course.C# and Teacher.T#= Course.T# and Teacher.Tname = 'ҶƼ' );
	*/
	
	
	//6����ѯѧ����001������Ҳѧ����š�002���γ̵�ͬѧ��ѧ�š�������
	
	//˼·�� 1 ͬʱѧ�����ſγ̵�ѧ��   2 ��ѯѧ����Ϣ
	//���ѵ㣺 �Ҳ�֪�� ��ô��ѯͬʱѡ�����ſε�ѧ��
	select Student.S#,Student.Sname from Student  where S# in 
	(select SC.S# from  SC where sc.C#  in('001','002'));

	/*
	 * ��ô��ѯͬʱѡ�����ſε�ѧ��
	 * Student.S# = SC.S# and SC.C# = '001' and exists (select * from SC as sc_2 
	 * where SC_2.S# = SC.S# and SC_2.C# = '002')
	  

	  select Student.s#,Student.Sname from Student,SC where Student.S# = SC.S# and SC.C# = '001' 
			and exists (select * from SC as sc_2 where SC_2.S# = SC.S# and SC_2.C# = '002');
	*/
	
	
	
	//7����ѯѧ����Ҷƽ����ʦ���̵����пε�ͬѧ��ѧ�š�������
	
	//˼·�� 1 ��ѯ ��ʦ���̵����п�   2��ѯ���п��������е�ѧ��  3 ��ѯѧ����Ϣ
	
	select T# from Teacher where Tname = 'Ҷƽ';
	
	select S# from SC ,Course where sc.c# = Course.C# and Course.T# = Teacher.T#;
	
	select Student.s#,Student.Sname from Student where Student.S# = SC.S#;
	
	
	select Student.s#,Student.Sname from Student where S# in 
	(select SC.S# from SC,Course,Tname 
			where sc.c# = Course.C# and Course.T# = Teacher.T# and Teacher.Tname ='Ҷƽ');
	
	/*
	 * û���뵽����  2��ѯ���п��������е�ѧ��  ==����ô��ѯ���пε����е�ѧ��
	 * 
	 * ��ѯ���пΣ�select count(C#) from  Course,Teacher 
	                   where Teacher.T# = Course.T# and Tname = 'Ҷƽ'
	 * 
	 * ��ѯ����ѧ����(select S# from SC ,Course ,Teacher 
	 * where SC.C#=Course.C# and Teacher.T#=Course.T# and Teacher.Tname='Ҷƽ'
		group by S# having count(SC.C#) = 
		(select count(C#) from  Course,Teacher where Teacher.T# = Course.T# and Tname = 'Ҷƽ');
	
	   ���п� ������ѧ�� ����������  ����Ҫ������  ���ܰ�
	  
	 select S#,Sname from Student  where S# in 
	(select S# from SC ,Course ,Teacher 
	where SC.C#=Course.C# and Teacher.T#=Course.T# and Teacher.Tname='Ҷƽ'
	group by S# having count(SC.C#) = 
	(select count(C#) from  Course,Teacher where Teacher.T# = Course.T# and Tname = 'Ҷƽ');
	*/
	
	
	//8����ѯ�γ̱�š�002���ĳɼ��ȿγ̱�š�001���γ̵͵�����ͬѧ��ѧ�š�������
	
	//˼·�� 1��ѯ 001 ��002 �γ̵�ѧ��  2 ��ѯѧ����Ϣ(������ѧ����ͬ,ͬʱ�ȽϷ�����С )
	
    select  b.S# from (select s# from SC where c# = '001') as a,
	   (select s# from sc where C# = '002') as b
		where a.score < b.score and a.S# = b.S#;
		select * from student where Student.S# = b.S#;
		
	//������
	select S#,Sname from (select sTudent.S#,student.sname,score,(select score from SC SC_2) 
			where SC_2.S# = Student.S# and SC_2.C#= '002') score2 
    from Student ,SC where Student.s# = SC.S# and c# = '001') s_2 where score2 < score;
	
	
	//9����ѯ���пγ̳ɼ�С��60�ֵ�ͬѧ��ѧ�š�������
	//˼·�� 1 �Ȳ�ѯͬѧ��ѧ�ź�����    2 ��ѯ���пγ̵ĳɼ���60 ��ѧ�� 
	//�ѵ㣺�������ű������м���ѧ��Snum������
	SELECT  s.Snum,s.Sname from student as s,sc where sc.Snum = s.Snum AND sc.Snum in
			(SELECT Snum from sc  where score < 60);

	/*
	 select s.Snum,s.Sname from student as s WHERE s.Snum NOT in 
	(SELECT s2.Snum from student as s2,sc where s2.Snum= sc.Snum AND score < 60);
	*/
	
	
	
	//10����ѯû��ѧȫ���пε�ͬѧ��ѧ�š�������
	//˼·�� 1��ѯ���пγ̵�����    2 ��ѯѧ�����пγ̵�ѧ�� Ȼ��not in  3 ��ѯ ͬѧ��ѧ�� ������
	
	SELECT Snum,Sname from student as s where s.Snum not  in 
	(SELECT Snum from sc GROUP BY Snum HAVING count(Cnum) = 5);
	
	SELECT Snum,Sname from student as s where s.Snum not  in 
	(SELECT Snum from sc GROUP BY Snum HAVING count(Cnum) = (SELECT count(Cnum) from course));
	
	SELECT s.Snum ,s.Sname from student as s ,sc where s.Snum = sc.Snum
			GROUP BY s.Snum,s.Sname HAVING count(Cnum) = (SELECT count(Cnum) from course);
	
	
	
	//11����ѯ������һ�ſ���ѧ��Ϊ��1001����ͬѧ��ѧ��ͬ��ͬѧ��ѧ�ź�������
	//˼·��1 ��ѯ��001��ͬѧ��ѧ�γ̵ı��     2 ��ѯѧ�ţ������ǿγ̱���ڡ�001��ͬѧ��ѧ�Ŀγ̱���� 
	//     3 ��ѯ ͬѧ��ѧ�ź�����
	
	SELECT s.sname,s.snum ,s.Sage from student as s where s.Snum in
	(SELECT Snum from sc where Cnum in (SELECT Cnum from sc  WHERE Snum = '1' ))
	
	SELECT s.sname,s.snum ,s.Sage from student as s where s.Snum in
	(SELECT Snum from sc  where Cnum in (SELECT Cnum from sc  WHERE Snum = '1' ))
	
	
	//12����ѯ����ѧ��ѧ��Ϊ��001��ͬѧ����һ�ſε�����ͬѧѧ�ź�������
	//˼·�� 1��ѯ ѧ��Ϊ��1����ѧ����ѧ�Ŀγ�    2 ���ݿγ̲�ѯ ѧ������Ϣ
	
	SELECT s.Sname,s.Snum from student as s,sc where s.Snum = sc.Snum and sc.Cnum in
	(select s2.Cnum from sc as s2 where s2.Snum = '1');
	
	/*
	 select distinct SC.S#,Sname from Student,SC 
     where Student.S#=SC.S# and C# in (select C# from SC where S#='001'); 
    */

	
	//13���ѡ�SC�����С�Ҷƽ����ʦ�̵Ŀεĳɼ�������Ϊ�˿γ̵�ƽ���ɼ���
	//Ϊʲô���ܲ�ѯ�� ���Ǵ�ģ�
	UPDATE  sc 
	set score = (SELECT avg(s2.score) from sc as s2 where s2.Cnum = sc.Cnum)
	from teacher,course  
	where course.Cnum = sc.Cnum and course.Tnum = teacher.Tnum and Tname = '�Ժ�';
	
	UPDATE sc  SET score = 
			(SELECT avg(s2.score) from sc as s2 where Cnum = s2.Cnum)
		where Cnum in 
			(SELECT course.Cnum 
			 from course ,teacher
			 where course.Tnum = teacher.Tnum 
			 and Tname='�����');
	
	
	//14����ѯ�͡�1002���ŵ�ͬѧѧϰ�Ŀγ���ȫ��ͬ������ͬѧѧ�ź�������
	/**
	 * 1   ��ѯ������Ϣ ѧ�š�2����ѧ����Ϣ
	 * 2   ���������  =  1���صı���
	 * 3   ����ѧ�ŷ���
	 * 4   ��ѯ�������� �γ� ��ȫ �͡�2���Ŀγ�һ��  ������Ϊʲôһ�� in �Ϳ����ˣ���
	 * 5   ��ѯѧ����Ϣ
	 */
	SELECT Snum from sc where Cnum in (SELECT Cnum from sc where Snum = '2')
	GROUP BY Snum 
	HAVING count(*) = (SELECT count(*) from sc where Snum ='2');
	
	
	//15��ɾ��ѧϰ��Ҷƽ����ʦ�ε�SC���¼��
	//�ѵ㣺 ���� ��� ����ɾ��
	//���Ǵ�ɾ������===�� ���ִ��ˣ�
	
	delete  s1 from sc as s1 ,course,teacher 
	where course.Cnum = s1.Cnum and course.Tnum = teacher.Tnum and Tname ='�����';
	
 /*   Delect SC 
    from course ,Teacher  
    where Course.C#=SC.C# and Course.T#= Teacher.T# and Tname='Ҷƽ';
 */

	/**
	 * ��ȷ�Ĳ�ѯ���
	 * delete from sc where cnum in 
		(SELECT course.Cnum from course, teacher 
		where course.Tnum = teacher.Tnum and Tname = '�Ժ�');
	 */
	
  //16����SC���в���һЩ��¼����Щ��¼Ҫ���������������
				//û���Ϲ���š�003���γ̵�ͬѧѧ�š�
				//2�� �ſε�ƽ���ɼ�
     
	INSERT sc SELECT Snum,'3',
	(SELECT avg(score) from sc where Cnum ='3')
	from student where Snum not in (SELECT Snum from sc where cnum = '3');
	
	
	
}	