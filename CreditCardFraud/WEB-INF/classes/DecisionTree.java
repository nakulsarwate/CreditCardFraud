
import java.io.*;
import javax.servlet.http.*;  
import javax.servlet.*;  
//import java.io.IOException;
import java.util.*;

import java.sql.*;

public class DecisionTree extends HttpServlet{

        int node_count=0;
        String attr1[]={"UID","IP","Amount"};
        int attr_list_count=3;
		public class Node{
			private String tandnt;
			private int nodeid;
			private String attr;
			private Node branch[];
			String branch_name[];
			int branch_count;
			
			public Node (int ni)
			{
			nodeid=ni;
			//attr=s;
			//tandnt="NT";
			}
		}
		
		
		String attribute_selection(ResultSet rs,  String attr[]) 
		{
			double count=0,count1=0,count2=0;
			String split="";
			
			try{
				rs.first();
			 do
			 {
				
			String type= rs.getString("type");
			if(type.equals("legit"))
			{
				count1++;
				
				}
			 
			else
			{
				count2++;
			}
			 }while(rs.next());
			 
			count=count1+count2;
			//System.out.println("No. of legitimate and fraudulent transactions "+ count1+" "+count2);
			
					double x1=count1/count; //P(legit/total)
					double log2=Math.log(x1)/Math.log(2);
					double x2=count2/count; //P(fraud/total)
					double log2_=Math.log(x2)/Math.log(2);
			double info_d= -(x1*log2)-(x2*log2_);
			//rs.first();
			System.out.println("Info_d "+info_d);
			double count_amt_l=0,count_amt_m=0,count_amt_h=0;
			double count_amt_l_l=0,count_amt_m_l=0,count_amt_h_l=0;
			double count_amt_l_f=0,count_amt_m_f=0,count_amt_h_f=0;
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Reached");
			java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb");
			Statement st = con.createStatement(); 
			String sql1="select * from Transactions where Amount= \"low\" ;";
			String sql3="select * from Transactions where Amount= \"medium\" ;";
			String sql5="select * from Transactions where Amount= \"high\" ;";
			ResultSet rs1= con.createStatement().executeQuery(sql1);
			//rs1.next();
			//System.out.println(rs1.getString("Amount"));
			ResultSet rs2 = con.createStatement().executeQuery(sql3);
			ResultSet rs3 = con.createStatement().executeQuery(sql5);
			//System.out.println("Test");
			//Error
			
			while(rs1.next())
			{
				//System.out.println("Inside loop");
				count_amt_l++;
			}
			while(rs2.next())
			{
				count_amt_m++;
			}
			while(rs3.next())
			{
				count_amt_h++;
			}
			//System.out.println("counts amounts l,m,h "+count_amt_l+ " "+count_amt_m+" "+count_amt_h);
			rs1.beforeFirst();
			rs2.beforeFirst();
			rs3.beforeFirst();
			while(rs1.next())
			{
				String type=rs1.getString("type");
				if(type.equals("legit"))
				{
					count_amt_l_l++;
					
				}
				else
				{
					count_amt_l_f++;
				}
				
			}
			//System.out.println("Low legit "+count_amt_l_l);
			while(rs2.next())
			{
				String type=rs2.getString("type");
				if(type.equals("legit"))
				{
					count_amt_m_l++;
					
				}
				else
				{
					count_amt_m_f++;
				}
				
			}
			while(rs3.next())
			{
				String type=rs3.getString("type");
				if(type.equals("legit"))
				{
					count_amt_h_l++;
					
				}
				else
				{
					count_amt_h_f++;
				}
				
			}
			//System.out.println("No of legit and fraud for high "+count_amt_h_l+" "+count_amt_h_f);
			double x_1=count_amt_l_l/count_amt_l;
			double x_2=count_amt_l_f/count_amt_l;
			double x_3=count_amt_m_l/count_amt_m;
			double x_4=count_amt_m_f/count_amt_m;
			double x_5=count_amt_h_l/count_amt_h;
			double x_6=count_amt_h_f/count_amt_h;
			double info_amt_l=count_amt_l/count*(-(x_1*(Math.log(x_1)/Math.log(2))-(x_2*(Math.log(x_2)/Math.log(2)))));
			double info_amt_m=count_amt_m/count*(-(x_3*(Math.log(x_3)/Math.log(2))-(x_4*(Math.log(x_4)/Math.log(2)))));
			double info_amt_h=count_amt_h/count*(-(x_5*(Math.log(x_5)/Math.log(2))-(x_6*(Math.log(x_6)/Math.log(2)))));
			double info_amt=info_amt_l+info_amt_m+info_amt_h;
			System.out.println("Info amt "+info_amt);
			double gain_amt=info_d-info_amt;
			System.out.println("gain_amt"+gain_amt);
			
			//ip
			sql1="select distinct IP from Transactions;";
			String sql2="select count(distinct IP) from Transactions;";
			//System.out.println("Reached");
			ResultSet rs_=con.createStatement().executeQuery(sql1);
			ResultSet rs__=con.createStatement().executeQuery(sql2);
			//System.out.println("Executed");
			rs__.next();
			int ln=rs__.getInt("count(distinct IP)");
			//System.out.println(ln);
			//rs_.next();
			String ip[]=new String[ln];
						int i=0;
	
			while(rs_.next())
				
			{
				ip[i]=rs_.getString("IP");
				//System.out.println(ip[i]+" ");
				i++;
				
			}
			
			int l=ip.length;
			i=0;
			ResultSet rs_ip[]=new ResultSet[l];
			while(i<l)
			{
				
				 sql2="select * from Transactions where IP = \""+ ip[i]+ "\" ;";
				 rs_ip[i] = con.createStatement().executeQuery(sql2);
				 i++;
			}
			
			i=0;
			double cnt1=0,cnt2=0,cnt=0;
			 i=0;
			 double info_ip=0;
			while(i<l)
			{
			while(rs_ip[i].next())
			{
				String type=rs_ip[i].getString("type");
				if (type.equals("legit"))
				{
					cnt1++;
				}
				else
				{
					cnt2++;
				}
				cnt++;
			}
			//System.out.println(cnt1+" "+cnt2);
			
			double info_ip_i=cnt/count*(-(cnt1*(Math.log((cnt1/cnt))/Math.log(2.0))-(cnt2*(Math.log((cnt2/cnt))/Math.log(2.0)))));
			//System.out.println(info_ip_i);
			info_ip=info_ip+info_ip_i;
			cnt=0;
			cnt1=0;
			cnt2=0;
			i++;
			}
			
			
			
			
			double gain_ip=info_d-info_ip;
			System.out.println("gain_ip"+gain_ip);
			
			//UID
			sql1="select distinct UID from Transactions;";
			sql2="select count(distinct UID) from Transactions;";
			 rs_=con.createStatement().executeQuery(sql1);
			rs__=con.createStatement().executeQuery(sql2);
			rs__.next();
			 ln=rs__.getInt("count(distinct UID)");
			//rs_.next();
			String uid[]=new String[ln];
						 i=0;
					//System.out.println(ln);
			
			while(rs_.next())
			{
				uid[i]=rs_.getString("UID");
				i++;
				//rs.next();
			}
			//rs.next();
			//ip[i]=rs.getString("IP");
			 l=uid.length;
			i=0;
			ResultSet rs_uid[]=new ResultSet[l];
			while(i<l)
			{
				
				 sql2="select * from Transactions where UID = \""+ uid[i]+ "\";";
				 rs_uid[i] = con.createStatement().executeQuery(sql2);
				 i++;
			}
			
//sql3="select * from Transactions where Amount='medium';";
			 //sql4="select * from Transactions where Amount='medium' and type='fraud';";
			 //sql5="select * from Transactions where Amount='high';";
			 //sql6="select * from Transactions where Amount='high' and type='fraud';";
			//ResultSet rs4 = st.executeQuery(sql1);
		//	ResultSet rs5 = st.executeQuery(sql3);
			//ResultSet rs6 = st.executeQuery(sql5);
			i=0;
			 cnt1=0;
			 cnt2=0;
			 cnt=0;
			 i=0;
			 double info_uid=0;
			 
			while(i<l)
			{
				rs_uid[i].beforeFirst();
				//System.out.println("Inside loop");
			while(rs_uid[i].next())
			{
				//System.out.println("Inside loop");
				//System.out.println(uid[i]);
				String type=rs_uid[i].getString("type");
				//System.out.println(type);
				if (type.equals("legit"))
				{
					cnt1++;
				}
				else
				{
					cnt2++;
				}
				cnt++;
			}
			
			double info_uid_i=cnt/count*(-(cnt1*(Math.log((cnt1/cnt))/Math.log(2))-(cnt2*(Math.log((cnt2/cnt))/Math.log(2)))));
			info_uid=info_uid+info_uid_i;
			//System.out.println("Counts "+" "+cnt1+" "+cnt2);
		//	System.out.println(info_uid_i);
			cnt=0;
			cnt1=0;
			cnt2=0;
			i++;
			}
			
			
			
			
			double gain_uid=info_d-info_uid;
			System.out.println("gain_uid "+gain_uid);
			
			//OTP
			
			
			// max info again
			double max_info_gain=0;
			
			int l1=attr.length;
			int c_uid=0,c_amt=0,c_ip=0;
			for(int j=0;j<l1;j++)
			{
				if(attr[j]=="UID")
				{
					c_uid++;
				}
				if(attr[j]=="IP")
				{
					c_ip++;
				}
				if(attr[j]=="Amount")
				{
					c_amt++;
				}
			}
			if(c_uid==0)
			{
				gain_uid=-100;
			}
			if(c_ip==0)
			{
				gain_ip=-100;
			}
			if(c_amt==0)
			{
				gain_amt=-100;
			}
		 max_info_gain=Math.max(gain_uid, Math.max(gain_ip, gain_amt));
			/*if(gain_amt > gain_ip)
			{
				if(gain_amt > gain_uid)
				{
					max_info_gain=gain_amt;
					split="Amount";
				}
				else 
				{
					max_info_gain=gain_uid;
					split="UID";
				}
			}
			if(gain_amt <  gain_ip)
			{
				if(gain_ip > gain_uid)
				{
					max_info_gain=gain_ip;
					split="IP";
				}
				else 
				{
					max_info_gain=gain_uid;
					split="UID";
					
				}
			}*/
		 if(max_info_gain==gain_uid)
		 {
			 split="UID";
		 }
		  if(max_info_gain==gain_ip)
		 {
			 split="IP";
		 }
		 if(max_info_gain==gain_amt)
		 {
			 split="Amount";
		 }
			System.out.println(split+" "+max_info_gain);
			
			return(split);
			
			
			
			
		}
			catch(Exception e)
			{
			System.out.println(e.getMessage());
			}
		
return(split);
			
		}
		Node generate_decision_tree(ResultSet rs,String attr[]){
			//DecisionTree ob = new DecisionTree();
			System.out.println("Entered");
			node_count++;
			
			Node n = new Node(node_count);// Line 1
			System.out.println("n nodeid "+n.nodeid);
			node_count++;
			Node n1=new Node(node_count);
			System.out.println("n1 nodeid "+n1.nodeid);
			boolean flag=true;
			
			try {
				
				rs.first();
				String type= rs.getString("type"); //Get first record
				System.out.println(type);
				
				while(rs.next())       
				{
				String type2= rs.getString("type");
				System.out.println(type2);
				if(!type.equals(type2))    //Compare each record with first
				{
					flag=false;
					System.out.println("Records not of the same type");
					break;
					
				}
				 }
				System.out.println(flag);
				if(flag==true)   //Line 2-3 ALL records of same type
				{
					//int brch=0; set branch_count of leaf to 0
					update_node(n,type,"T",0);
					return(n);   //return n as leaf labeled with type
				}
				
				System.out.println("Here");
				/*boolean flag2=true;
				System.out.println(flag2);
				//error
				int c=0;
				for(int k=0;k<3;k++)
				{
					if(attr[k].equals(""))
					{
						c++;
					}
				}*/
				//System.out.println(flag2);
			//	int l=attr.length; //Line 4-5
				//int l=3;
				int cnt=0;
			/*	for(int j=0;j<l;j++)
				{
					if(attr1[j].equals(null))
					{
						cnt++;
					}
				}
				rs.beforeFirst();
				*/
				int count=0,count2=0;
				
				//System.out.println(attr_list_count);
				//System.out.println("Here");
				if(attr_list_count==0)   //attr list empty
				{
					//int count=0,count2=0;
					System.out.println("Attribute list empty");
					rs.beforeFirst();
				     while(rs.next())
					 {
					type= rs.getString("type");
					if(type.equals("legit"))
					{
						count++;
						
						}
					 
					else
					{
						count2++;
					}
					 }
					if(count<=count2) //check
					{
						//int brch=0;
						update_node(n,"fraud","T",0);
						return(n);  //return n as leaf labeled with majority class
					}
					else 
					{
						//int brch=0;
						update_node(n,"legit","T",0);
						return(n);
					}
				
				}
				
					
				//Line 6
					System.out.println("Attribute list is not empty");
					int brch=0;
					System.out.println("Before selecting split criteria");
					
        String split= attribute_selection(rs,attr);
        System.out.println("After splitting criteria: "+split);
        
        int c_split=0;
        try{
        Class.forName("com.mysql.jdbc.Driver");
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb");
		Statement st = con.createStatement(); 
        
        String sql="select distinct "+split+" from Transactions;"; //check sql syntax
        ResultSet rs3=con.createStatement().executeQuery(sql);
       // String s="count(distinct "+split+" ) from Transactions;";
        
        while(rs3.next())
        {
        	c_split++;
        }
       // brch=rs3.getInt(s);
        brch=c_split;
        System.out.println("No. of outcomes of splitting criteria: "+c_split);
        
        
        
        update_node(n,split,"NT",brch);   //line 7 label n with splitting criteria
        System.out.println("N.attr, N.branch_count "+n.attr+" "+n.branch_count);
        int branch_index=0;
         int l=attr.length;
        for(int i=0;i<l;i++)
        {
        	if(split.equals(attr[i]))
        		
        	{
        		//System.out.println(attr1[i]);
        		
        		attr_list_count--; //line 9 delete splitting criteria from attr list
        	}
        }
       
        String a[]=new String[attr_list_count];
        int len123=attr.length;
		int o=0;
		for(int m=0;m<len123;m++)    //new list
		{
			if(!attr[m].equals(split))
				{a[o]=attr[m];o++;//System.out.println(a[o]);
				}
			
		}
		l=a.length;
		 System.out.println("After deleting from attribute list: ");
		 
		 for(int i=0;i<l;i++)
	        {
	        	System.out.println(a[i]);
	        }
       
        //Lines 10,11
        
        /*int cnt2=0;
        while(rs1.next()){
        	cnt2++;
        }
        rs1.first();
        */
        rs3.beforeFirst();
        while(rs3.next())
        {
        	String outcome=rs3.getString(split);
        	System.out.println("SPlit="+split);
        	System.out.println("Outcome="+outcome);
        	String sql3="select * from Transactions where "+split+" = \"" + outcome+"\";";
        	ResultSet rs2=con.createStatement().executeQuery(sql3); //rs2 is Dj
        	if(!rs2.next())   //if Dj empty lines 12-13
        	{	//Node n1=new Node();
        	      System.out.println("Dj is empty");
        		if(count<=count2)
				{
					update_node(n1,"fraud","T",0); //attach leaf labeled with majority class
					attach_node(n,n1,branch_index,outcome);
					System.out.println("Attribute: "+n1.attr);
					branch_index++;
					
				}
				else
				{
					update_node(n1,"legit","T",0);
					attach_node(n,n1,branch_index,outcome);
					System.out.println("Attribute: "+n1.attr);
					branch_index++;
				}
        	}
        	//line 14
        	else
        	{
        		System.out.println("Dj is not empty");
        		int len=attr_list_count;
        		// jnkj
        		
        		
        		n1=generate_decision_tree(rs2,a);
        		System.out.println(n1.attr);
        		attach_node(n,n1,branch_index,outcome);
        		System.out.println("Attribute: "+n1.attr);
        		branch_index++;
        	}
        	
        }
        
        
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
        
      // return(n); 
				
				
			 
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return(n); 
			
		}
		void update_node(Node n, String at,String qora,int brch)
		{
			n.tandnt=qora;
			n.attr=at;
			n.branch_count=brch;
			n.branch=new Node[brch];
			n.branch_name=new String[brch];
		}
		void attach_node(Node n, Node n1,int branch_index,String outcome)
		{
			n.branch[branch_index]=n1;
			n.branch_name[branch_index]=outcome;
			
		}
		String traverse_classify(Node n_,String uid, String ip, String amount)
		{
			Node n1_;
			 
			while(!n_.tandnt.equals("T"))
			{
			if(n_.attr.equals("Amount"))
			{
				for(int i=0;i<n_.branch_count;i++)
					{
						
						if(n_.branch_name[i].equals(amount))
						{
							n1_=n_.branch[i];
							
							
								n_=n1_;
							
						    //break;
						}
						
					}
					
				}
				if(n_.attr.equals("IP"))
				{
					for(int i=0;i<n_.branch_count;i++)
						{
							
							if(n_.branch_name[i].equals(ip))
							{
								n1_=n_.branch[i];
								
								
									n_=n1_;
								
							   // break;
							}
							
							
						
					}
				}
					if(n_.attr.equals("UID"))
					{
						for(int i=0;i<n_.branch_count;i++)
							{
								
								if(n_.branch_name[i].equals(uid))
								{
									n1_=n_.branch[i];
									n_=n1_;
							
								   // break;
								}
								}
			}
			}
					if(n_.attr.equals("legit")||n_.attr.equals("fraud"))
					{
						
						return(n_.attr);
					}
					
			return "Something went wrong";
		}

		
		public void doGet(HttpServletRequest req,HttpServletResponse res)  
				throws ServletException,IOException  
				{  
						String contextPath= "";
						
				   
				//writing html in the stream  
				
				
				//closing the stream 

			DecisionTree ob=new DecisionTree();
			DecisionTree.Node n_;
			//System.out.println()OTP
			//String attr1[]={"UID","IP","Amount"}; //

		
			try{

				Class.forName("com.mysql.jdbc.Driver");
				 
				  
				java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb"); 
				Statement st = con.createStatement(); 
				Statement st2 = con.createStatement(); 
				  
				  
				ResultSet rs = st.executeQuery("select * from Transactions;");
				n_=ob.generate_decision_tree(rs,ob.attr1);
				System.out.println("Tree generated");
				System.out.println(n_.nodeid);
				
				//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				
				ResultSet rsfinal = st.executeQuery("select * from Current;");
				ResultSet rscnt= st2.executeQuery("select count(*) from Transactions;");
				rscnt.next();
				int cnt12=rscnt.getInt("count(*)");
				cnt12++;
				String scnt12="T"+cnt12;
                //Resultset rsfinal123 = con.createStatement.executeUpdate("Update Current set TID=\""+scnt12+"\" where TID=\"T\";");
                //Resultser rsfinal234 = con.createStatement.executeUpdate("Update Current set type=\""+type+"\" where type=\"Ty\";");
				



				rsfinal.next();
				String uid=rsfinal.getString("UID");
				String ip=rsfinal.getString("IP");
				String amount=rsfinal.getString("Amount");

				String type=ob.traverse_classify(n_,uid,ip,amount);


				int rsfinal3 = st2.executeUpdate("Delete from Current;");
				Statement stu = con.createStatement(); 
				String sql3="insert into Transactions values(\""+scnt12+"\",\""+uid+"\",\""+ip+"\",\""+amount+"\",\""+type+"\");";
				int rsu1=con.createStatement().executeUpdate(sql3);




				//int rsfinal2 = con.createStatement.executeUpdate("Insert into Transactions select * from Current;");
				//rsfinal2 = st2.executeUpdate("Update Transactions set TID=\""+scnt12+"\" where TID is NULL;");
				
				
				
				//System.out.println("Enter UID, IP, Amount: ");
				//String uid=br.readLine();
				//String ip=br.readLine();
				
				//String amount=br.readLine();
			
				
				
				
				
				if(type.equals("legit"))
			{
				
				
                   res.sendRedirect(res.encodeRedirectURL(contextPath + "/Legit.html"));
				}
				else
			{
				
                    res.sendRedirect(res.encodeRedirectURL(contextPath + "/Fraud.html"));
			}
				
				/*while(rs.next()) 
				 {
				String name = rs.getString("Name");
				int roll = rs.getInt("Roll");

				System.out.print("" + name);
				System.out.println(" " + roll);
				}*/
				//res.sendRedirect(res.encodeRedirectURL(contextPath));
					}
					 

				catch(Exception e){
					System.out.print(e.getMessage());
				}
				
			
		}
		
		
		
}
