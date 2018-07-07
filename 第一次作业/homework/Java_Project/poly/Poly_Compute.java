package poly;

import java.util.Scanner;

public class Poly_Compute {
	private Poly[] PolyList;
	private char[] OpList;
	
	private int num = 0;
	private int pair_num = 0;
	private Poly p;
	
	public int error;
	
	Poly_Compute(){//���캯��
		p = new Poly(1000000);
		PolyList = new Poly[20];
		OpList = new char[20];
		error = 0;
		num = 0;
		pair_num = 0;
	}
	
	private void compute(){//����
		Poly q;
		char op;
		for(int i=0;i<num;i++){
			q = PolyList[i];
			op = OpList[i];
			if(op=='+')			
				p.add(q);
			else if(op=='-')	
				p.sub(q);
		}
		PolyList[0] = p;
	}
	
	private void output(){//���
		if(error!=0){
			//System.out.println("get error");
			error_throw();
		}
		else{
			int commal_flag = 1;
			System.out.print("{");
			for(int i=0;i<1000000;i++){
				if(p.get_Coeff(i)!=0){
					if(commal_flag==0){
						System.out.print(",");
						commal_flag = 1;
					}
					System.out.print("("+p.get_Coeff(i)+","+i+")");
					commal_flag = 0;
				}
			}
			System.out.print("}");
		}
	}
	
	private void Analyse_Data(String data){//�����ַ���
		int flag = 0;//ȷ��op��{}��λ�ù�ϵ
		int has_brackets = 0;
		for(int i=0;i<data.length();i++){
			if(error!=0)	return;
			char ch = data.charAt(i);
			if(ch=='{'){
				has_brackets = 1;
				if(flag==0)	report(1);//{}ǰ��û��op
				else{//flag = 1
					PolyList[num] = new Poly();
					i = Analyse_big(data,i);
					num++;
					pair_num = 0;
					flag = 0;
				}
			}
			else if(ch=='+'||ch=='-'){
				if(flag==1)	report(2);//opǰ��û��{}
				else{//flag = 0
					flag = 1;
					if(num>=20)	report(3);//����ʽ������20
					else{
						OpList[num] = ch;
					}
				}
			}
			else{
				report(4);//�����}
			}
		}
		if(has_brackets!=1)	report(1);
	}
	private int Analyse_big(String data,int i){//����������ڵ�����
		int j = i+1;
		int flag = 0;//ȷ��,��()��λ�ù�ϵ
		if(j>=data.length()-1){
			report(16);
			return 0;
		}
		if(data.charAt(i+1)!='(')	report(5);
		while(data.charAt(j)!='}'){
			if(error!=0)	return 0;
			if(j>=data.length()-1){
				report(16);
				return 0;
			}
			if(data.charAt(j)=='('){
				if(flag==1)	report(6);//�����м�û��,
				else{//flag = 0
					if(pair_num>=50){//���Գ���50
						//System.out.println(pair_num);
						report(14);
					}
					else{
						j = Analyse_l(data,j);
						pair_num++;
						flag = 1;
					}
				}
			}
			else if(data.charAt(j)==','){
				if(flag==0) report(7);//,ǰ��û�н���������
				else{//flag = 1
					flag = 0;
				}
			}
			else{
				report(8);//�����)
			}
			if(j>=data.length()-1){
				report(16);
				return 0;
			}
			j++;
		}
		if(flag == 0)	report(5);
		if(error!=0)	return 0;
		return j;
	}
	
	private int Analyse_l(String data,int j){//����С�����ڵ�����
		int k = j+1;
		String str1 = "";
		String str2 = "";
		
		if(k>=data.length()-1){
			report(16);
			return 0;
		}
		
		while(data.charAt(k)!=','){
			if(k>=data.length()-1){
				report(16);
				return 0;
			}
			str1 += data.charAt(k);
			k++;
		}
		if(k>=data.length()-1){
			report(16);
			return 0;
		}
		k++;
		while(data.charAt(k)!=')'){
			if(k>=data.length()-1){
				report(16);
				return 0;
			}
			str2 += data.charAt(k);
			k++;
		}
		
		int c = cal(str1);
		int n = cal(str2);
		
		if(error!=0)	return 0;
		
		if(n<0){
			report(9);//�ݲ���Ϊ����
			return 0;
		}
		else if(c==0){
			report(18);
			return 0;
		}
		else{
			//����c��n
			if(PolyList[num].get_Coeff(n)==0){
				PolyList[num].set_Coeff(c,n);
			}
			else report(15);//n�����ظ�
			
			if(n>PolyList[num].get_Degree())
				PolyList[num].set_Degree(n);
		}		
		return k;
	}
	
	private int cal(String str){
		char r = '+';
		int record_num = 0;
		int has_num = 0;
		
		if(str.length()==0) report(17);//����Ϊ��
		
		for(int t=0;t<str.length();t++){
			if(error!=0)	return 0;
			if((str.charAt(t)=='+'||str.charAt(t)=='-')){
				if(t==0)//-1,+2����
					r = str.charAt(t);
				else{
					report(10);//1+2����
					return 0;
				}
			}
			else if(Character.isDigit(str.charAt(t))){
				has_num = 1;
				int h = 0;
				String tmp_str = "";
				for(h=t;h<str.length();h++){
					if(Character.isDigit(str.charAt(h))){
						tmp_str += str.charAt(h);
					}
					else{
						report(11);//���ַ�����
					}	
				}
				t = h;
				/*�˴�ԭ��Ϊ�˴���ǰ��0ʹ�䲻ռλ��*/
				/*int splice_0 = -1;
				for(int s=0;s<tmp_str.length();s++){
					if(tmp_str.charAt(s)!='0'){
						splice_0 = s;
						break;
					}
				}
				if(splice_0<0)
					splice_0 = tmp_str.length()-2;
				tmp_str = tmp_str.substring(splice_0);*/
				if(tmp_str.length()>6)	report(12);//����1000000
				else{
					record_num = Integer.parseInt(tmp_str);
					record_num = r=='+'?record_num:(-record_num);
				}
			}
			else{
				report(13);//���ַ�����
			}
		}
		if(has_num==0)	report(17);//ȱ������
		return record_num;
	}
	
	private void report(int d){
		//System.out.println("error"+d);
		error = d;
	}
	
	private void error_throw(){
		System.out.print("get Error! ");
		switch(error){
			case 1:
			case 2:
			case 4:
				System.out.print("�����Ų�����,����{����ʽ}֮����ڴ�����ַ���ϻ�ȱ����Ӧ�ַ�, "); break;
			case 3:
				System.out.print("����ʽ��������20, "); break;
			case 5:
				System.out.print("�����ź�С����֮����ڴ����ַ���ȱ����Ӧ�ַ�, "); break;
			case 6:
			case 7:
			case 8:
				System.out.print("{����ʽ}�ڲ����ڴ�����ַ���ϻ�ȱ����Ӧ�ַ�, "); break;	
			case 9:
				System.out.print("ָ��nΪ����, "); break;
			case 10:
			case 11:
			case 13:
			case 17:
				System.out.print("(����)�ڲ����ڴ�����ַ���ϻ�ȱ����Ӧ�ַ�, "); break;	
			case 12:
				System.out.print("(����)��ϵ����ָ��������Χ, "); break;
			case 14:
				System.out.print("����ʽ�����Գ���50, "); break;
			case 15:
				System.out.print("ָ��n�����ظ�, "); break;
			case 16:
				System.out.print("����ƥ�䲻��ȷ, "); break;
			case 18:
				System.out.print("����ϵ��Ϊ0 ָ����Ϊ0������, "); break;
			default:
				System.out.print("������ڲ��Ϸ��ַ�, ");
				
		}
		System.out.println("����������:");
	}

	public static void main(String[] args) {	
		//String line = "+{(-12,2),(2,3)}-{(-2,2),(1,4)}+{(3,2)}+{(121,0),(666,999999),(-999999,12)}";
		int global_error = -1;
		Scanner s_in = new Scanner(System.in);
		
		while(global_error!=0){
			global_error = -1;
			String line = "";
			try{
				line = s_in.nextLine();
			}
			catch(Exception e){
				//global_error = 1;
				s_in.close();
				System.out.println("get Error! ��������쳣, ���������г���:");
				//continue;
				return;
			}
			
			line = line.replaceAll(" ","");
			if(line.length()==0){
				global_error = 1;
				System.out.println("get Error! ����Ϊ��, ����������:");
			}
			
			for(int i=0;i<line.length();i++){
				if(line.charAt(i)!='+'&&line.charAt(i)!='-'&&line.charAt(i)!=','
						&&line.charAt(i)!='{'&&line.charAt(i)!='}'
						&&line.charAt(i)!='('&&line.charAt(i)!=')'
						&&!Character.isDigit(line.charAt(i))){
					global_error = 1;
					System.out.println("get Error! ������ڲ��Ϸ��ַ�, ����������:");
					break;
				}
			}
			
			if(global_error==-1){
				if(line.charAt(0)!='+'&&line.charAt(0)!='-')
					line = "+"+line;
				
				Poly_Compute cp = new Poly_Compute();
				
				cp.Analyse_Data(line);
				if(cp.error!=1)
					cp.compute();
				
				cp.output();
				
				global_error = cp.error;
			}
		}
		s_in.close();	
	}
}


