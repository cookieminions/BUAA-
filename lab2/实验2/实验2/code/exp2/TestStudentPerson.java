package exp2;

public class TestStudentPerson {
	public static void main(String[] args)
	{
		Person[] person=new Person[4];
		
		
		person[0]=new Student("Jin",'M',20,"201504135146","061501");//���һ��ѧ����Ϣ
		person[0].register();//ѧ��ע��
	    person[0].updateAge(23);//���¸���������
	    person[0].toString();//��ӡ���
	    
	    
	    person[1]=new Person("Kate",'F',21);//���һ��ѧ����Ϣ
	    Student stu=(Student)person[1];
	    stu.register();//ѧ��ע��
	    stu.updateAge(25);//���¸���������
	    stu.toString();//��ӡ���
	    stu.printBasicInfo();//��ӡ���������Ϣ
	    
	    person[2]=new Teacher("Rene",'M',35,"06","01452");//���һ����ʦ��Ϣ
	    person[2].register();//��ɽ�ʦ��ע�ᣬ��¼ע��ʱ�䲢�趨����н��
	    person[2].toString();
	    
	    person[3]=new Person("Jason",'M',41);//���һ����ʦ��Ϣ
	    Teacher te=(Teacher)person[3];
	    te.register();//ѧ��ע��
	    te.printDetailInfo();//��ӡ���
	    }
	    
	    
	    
	    
	}   
		
		
