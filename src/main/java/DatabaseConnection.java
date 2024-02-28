/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import java.util.*;
import java.sql.*;
public class DatabaseConnection {
   static Connection conn;
    static Statement stm;
    PreparedStatement pst;
    ResultSet result;
    public static void getConnection() throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("get driver...");
        String url="jdbc:mysql://localhost:3306/studb";
        String name="root";
        String password="";
        conn=DriverManager.getConnection(url,name,password);
	System.out.println("get connection..."); 
}
 public boolean Login(String uname,String pass,String type) throws ClassNotFoundException, SQLException{
     getConnection();
     boolean flag=false;
     String sql="select userName,Password,Type from  user where userName=? and Password=? and Type=?";
		 pst=conn.prepareStatement(sql);
                pst.setString(1,uname);
                pst.setString(2,pass);
                pst.setString(3,type);
                result=pst.executeQuery();
                if(result.next()){
                    flag=true;
                }
                
     return flag;
 }   
     public void Register(Student stu) throws ClassNotFoundException, SQLException
{

String name=stu.getName();
String nrc=stu.getNrc();
String fname=stu.getFname();
String dob=stu.getDob();
String coursename=stu.getCourseName();
String nationality=stu.getNationality();
String education=stu.getEducation();
String phoneno=stu.getPhoneno();
String stuid=stu.getStudentid();
getConnection();
String sql="insert into student(studentid,studentname,nrc,fathername,dob,coursename,nationality,education,phoneno) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst=conn.prepareStatement(sql);
	
		
		pst.setString(1,stuid);
                pst.setString(2,name);
		pst.setString(3,nrc);
                pst.setString(4,fname);
                pst.setString(5,dob);
		pst.setString(6,coursename);
		pst.setString(7,nationality);
		pst.setString(8,education);
		pst.setString(9,phoneno);
		int rst=pst.executeUpdate();
		if(rst>0){
			System.out.println("Insert successful");
		}
			else{
				System.out.println("Insert unsuccessful");
			}
		
		//execute a query
		
		//step7 close	the connection
		conn.close();

   }
     
     public String[] getAllCourseName() throws ClassNotFoundException,SQLException{
        getConnection();
        ArrayList<String> list=new ArrayList<String>();
        String sql="Select coursename from course";
        pst=conn.prepareStatement(sql);
        
        result=pst.executeQuery();
        while(result.next()){
            list.add(result.getString(1));
        }
        String[] Id=list.toArray(new String[list.size()]);
        conn.close();
        return Id;
    }
   
     
     public String[] getAllTableCourseName() throws ClassNotFoundException,SQLException{
        getConnection();
        ArrayList<String> list=new ArrayList<String>();
        String sql="Select distinct(coursename) from student";
        pst=conn.prepareStatement(sql);
        
        result=pst.executeQuery();
        while(result.next()){
            list.add(result.getString(1));
        }
        String[] Id=list.toArray(new String[list.size()]);
        conn.close();
        return Id;
    }
    
  public String[] getIdList(String coursename)throws ClassNotFoundException,SQLException{
        getConnection();
        ArrayList<String> list=new ArrayList<String>();
        String query="select courseid from course where coursename=?";
        pst=conn.prepareStatement(query);
        pst.setString(1,coursename);
        result=pst.executeQuery();
        while(result.next()){
            list.add(result.getString(1));
        }
        String[] rno=list.toArray(new String[list.size()]);
        conn.close();
        return rno;
    }  
  
    public String[] getStuIdList(String coursename) throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
        String query = "select studentid from student where coursename=?";
        pst = conn.prepareStatement(query);
        pst.setString(1, coursename);
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
        String[] rno = list.toArray(new String[list.size()]);
        conn.close();
        return rno;
    }

  
     public void Delete(Student stu) throws ClassNotFoundException,SQLException{
        getConnection();
        String stuid=stu.getStudentid();
        String coursename=stu.getCourseName();
        String sql="delete from student where studentid=? and coursename=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,stuid);
        pst.setString(2,coursename);
        System.out.println(stuid+" "+coursename);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Successful");
        }
        else
        {
            System.out.println("unsuccessful");
        }
    }
    
    public String[][] getData() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select * from student";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
         stu.setStudentid(result.getString(1));
         stu.setName(result.getString(2));
         stu.setNrc(result.getString(3));
         stu.setFname(result.getString(4));
         stu.setDob(result.getString(5));
         stu.setCourseName(result.getString(6));
         stu.setNationality(result.getString(7));
         stu.setEducation(result.getString(8));
          stu.setPhoneno(result.getString(9));
         list.add(stu);
     }
     String[][] data=new String[list.size()][9];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
         data[i][0]=stu.getStudentid();
         data[i][1]=stu.getName();
         data[i][2]=stu.getNrc();
         data[i][3]=stu.getFname();
         data[i][4]=stu.getDob();
         data[i][5]=stu.getCourseName();
        data[i][6]=stu.getNationality();
        data[i][7]=stu.getEducation();
        data[i][8]=stu.getPhoneno();
     }
     conn.close();
     return data;
 }

    public Student getAllDataByStudentIdCourseName(String studentid,String coursename)throws ClassNotFoundException,SQLException{
        getConnection();
        String id=studentid;
        String sql="select studentname,nrc,fathername,dob,education,phoneno from student where studentid=? and coursename=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,id);
        pst.setString(2,coursename);
        result=pst.executeQuery();
        Student stu=new Student();
        while(result.next()){
            stu.setName(result.getString(1));
            stu.setNrc(result.getString(2));
            stu.setFname(result.getString(3));
            stu.setDob(result.getString(4));
            stu.setEducation(result.getString(5));
            stu.setPhoneno(result.getString(6));
        }
        conn.close();
        return stu;
    }

    public void Update(Student stu) throws ClassNotFoundException,SQLException{
        
        String name=stu.getName();
        String nrc=stu.getNrc();
        String fathername=stu.getFname();
        String dob=stu.getDob();
        String coursename=stu.getCourseName();
        String nationality=stu.getNationality();
        String education=stu.getEducation();
        String phoneno=stu.getPhoneno();
        String stuid=stu.getStudentid();
        getConnection();
        String sql="update student set studentname=?,nrc=?,fathername=?,dob=?,coursename=?,nationality=?,education=?,phoneno=? where studentid=? and coursename=?";
        PreparedStatement pst=conn.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2,nrc);
        pst.setString(3,fathername);
        pst.setString(4,dob);
        pst.setString(5,coursename);
        pst.setString(6,nationality);
       pst.setString(7,education);
        pst.setString(8,phoneno);
         pst.setString(9,stuid);
          pst.setString(10,coursename);
         int rst=pst.executeUpdate();
         if(rst>0)
             System.out.println("Update successful");
         else
             System.out.println("Update unsuccessful");
         conn.close();
    }
    
     public String[] getAllCourseIdClass() throws ClassNotFoundException,SQLException{
        getConnection();
        ArrayList<String> list=new ArrayList<String>();
        String sql="Select courseid from course";
        pst=conn.prepareStatement(sql);
        
        result=pst.executeQuery();
        while(result.next()){
            list.add(result.getString(1));
        }
        String[] Id=list.toArray(new String[list.size()]);
        conn.close();
        return Id;
    }
     
     
      
      
      public void RegisterCourse(Student stu) throws ClassNotFoundException, SQLException
{

String name=stu.getName();
int fee=stu.getFee();
String duration=stu.getDuration();

getConnection();
String sql="insert into course(coursename,fees,duration) values(?,?,?)";
		PreparedStatement pst=conn.prepareStatement(sql);
	
		
		pst.setString(1,name);
                pst.setInt(2,fee);
		pst.setString(3,duration);
                
		int rst=pst.executeUpdate();
		if(rst>0){
			System.out.println("Insert successful");
		}
			else{
				System.out.println("Insert unsuccessful");
			}
		
		//execute a query
		
		//step7 close	the connection
		conn.close();

   }
      
       public Student getAllDataByCourseName(String coursename)throws ClassNotFoundException,SQLException{
        getConnection();
        
        String sql="select fees,duration from course where  coursename=?";
        pst=conn.prepareStatement(sql);
        
        pst.setString(1,coursename);
        result=pst.executeQuery();
        Student stu=new Student();
        while(result.next()){
            stu.setFee(result.getInt(1));
            stu.setDuration(result.getString(2));
           
        }
        conn.close();
        return stu;
       }
       
       public void UpdateCourse(Student stu) throws ClassNotFoundException,SQLException{
        
        int fee=stu.getFee();
        String duration=stu.getDuration();
        String coursename=stu.getCourseName();
        
        getConnection();
        String sql="update course set fees=?,duration=? where  coursename=?";
        PreparedStatement pst=conn.prepareStatement(sql);
        pst.setInt(1, fee);
        pst.setString(2,duration);
        pst.setString(3,coursename);
        
         int rst=pst.executeUpdate();
         if(rst>0)
             System.out.println("Update successful");
         else
             System.out.println("Update unsuccessful");
         conn.close();
    }
       
       public void DeleteCourse(Student stu) throws ClassNotFoundException,SQLException{
        getConnection();
       
        String coursename=stu.getCourseName();
        String sql="delete from course where  coursename=?";
        pst=conn.prepareStatement(sql);
        
        pst.setString(1,coursename);
        System.out.println(coursename);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Successful");
        }
        else
        {
            System.out.println("unsuccessful");
        }
    }
       
       
       public String[] getCourseIdList() throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
    String  query = "select CourseId from course";
        pst = conn.prepareStatement(query);
       
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
       String[] rno = list.toArray(new String[list.size()]);
        conn.close();
        return rno;
    }
       public String[][] getCourseData() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select * from course";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
         stu.setCid(result.getInt(1));
         stu.setCourseName(result.getString(2));
         stu.setFees(result.getString(3));
         stu.setDuration(result.getString(4));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][9];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
         data[i][0]=String.valueOf(stu.getCid());
         data[i][1]=stu.getCourseName();
         data[i][2]=stu.getFees();
         data[i][3]=stu.getDuration();
        
     }
     conn.close();
     return data;
 }
    
  public String[][] getAllCourseData() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select CourseName,Fees,Duration from course";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
       
         stu.setCourseName(result.getString(1));
         stu.setFees(result.getString(2));
         stu.setDuration(result.getString(3));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][3];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
        
         data[i][0]=stu.getCourseName();
         data[i][1]=stu.getFees();
         data[i][2]=stu.getDuration();
        
     }
     conn.close();
     return data;
 } 
  public String[][] getAllClassData() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select course.CourseName,class.StartDate,class.EndDate,class.Sensei from class inner join course on class.CourseId=course.CourseId";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
       
         stu.setCourseName(result.getString(1));
         stu.setStartDate(result.getString(2));
         stu.setEndDate(result.getString(3));
         stu.setSensei(result.getString(4));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][4];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
        
         data[i][0]=stu.getCourseName();
         data[i][1]=stu.getStartDate();
         data[i][2]=stu.getEndDate();
         data[i][3]=stu.getSensei();
     }
     conn.close();
     return data;
 } 
  public String[][] getAllClassData1() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select * from class";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
         
         
         stu.setStartDate(result.getString(2));
         stu.setEndDate(result.getString(3));
         stu.setCourseid(result.getString(4));
         stu.setSensei(result.getString(5));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][5];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
        
        
         data[i][0]=stu.getStartDate();
         data[i][1]=stu.getEndDate();
         data[i][2]=stu.getCourseid();
         data[i][3]=stu.getSensei();
     }
     conn.close();
     return data;
 } 
  public String[][] getAllClassData2ForView() throws ClassNotFoundException,SQLException
 {
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select class.ClassId,class.StartDate,class.EndDate,course.CourseName,class.Sensei from class inner join course on class.CourseId=course.CourseId";
     pst=conn.prepareStatement(query);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
         stu.setClaid(result.getInt(1));
          stu.setStartDate(result.getString(2));
         stu.setEndDate(result.getString(3));
         stu.setCourseName(result.getString(4));
        
         stu.setSensei(result.getString(5));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][5];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
        
         data[i][0]=String.valueOf(stu.getClaid());
         data[i][1]=stu.getStartDate();
         data[i][2]=stu.getEndDate();
         data[i][3]=stu.getCourseName();
         data[i][4]=stu.getSensei();
     }
     conn.close();
     return data;
 } 
  public String[][] getAllClassData2ForView1(String classId) throws ClassNotFoundException,SQLException
 {
     int cid=Integer.parseInt(classId);
     getConnection();
     ArrayList<Student> list=new ArrayList<Student>();
     String query="select class.ClassId,class.StartDate,class.EndDate,course.CourseName,class.Sensei from class inner join course on class.CourseId=course.CourseId where ClassId=?";
     pst=conn.prepareStatement(query);
     pst.setInt(1, cid);
     result=pst.executeQuery();
     while(result.next())
     {
         Student stu=new Student();
         stu.setClaid(result.getInt(1));
          stu.setStartDate(result.getString(2));
         stu.setEndDate(result.getString(3));
         stu.setCourseName(result.getString(4));
        
         stu.setSensei(result.getString(5));
         
         list.add(stu);
     }
     String[][] data=new String[list.size()][5];
     for(int i=0;i<list.size();i++)
     {
         Student stu=list.get(i);
        
         data[i][0]=String.valueOf(stu.getClaid());
         data[i][1]=stu.getStartDate();
         data[i][2]=stu.getEndDate();
         data[i][3]=stu.getCourseName();
         data[i][4]=stu.getSensei();
     }
     conn.close();
     return data;
 } 
  public boolean ClassInsert(Student stu) throws ClassNotFoundException, SQLException
{

String startdate=stu.getStartdate();
String enddate=stu.getEnddate();
String courseid=stu.getCourseid();
String sensei=stu.getSensei();
getConnection();
String sql="insert into class(startdate,enddate,courseid,Sensei) values(?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
	
		boolean flag=false;
		ps.setString(1,startdate);
                ps.setString(2,enddate);
		ps.setString(3,courseid);
                ps.setString(4,sensei);
		int rst=ps.executeUpdate();
		if(rst>0){
			flag=true;
		}
			
		
		//execute a query
		
		//step7 close	the connection
		conn.close();
return flag;
   }
     
   public String[] getClassId() throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
    String  query = "select CourseId from class";
        pst = conn.prepareStatement(query);
       
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
       String[] id = list.toArray(new String[list.size()]);
        conn.close();
        return id;
    }
   public String[] getClassId1() throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
    String  query = "select ClassId from class";
        pst = conn.prepareStatement(query);
       
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
       String[] id = list.toArray(new String[list.size()]);
        conn.close();
        return id;
    }
   
   public void UpdateClass(Student stu) throws ClassNotFoundException,SQLException{
        
        String sdate=stu.getStartdate();
        String edate=stu.getEnddate();
        String c=stu.getCourseid();
        
        getConnection();
        String sql="update class set startdate=?,enddate=? where  courseid=?";
        PreparedStatement pp=conn.prepareStatement(sql);
        pp.setString(1, sdate);
        pp.setString(2,edate);
        pp.setString(3,c);
        
         int rst=pp.executeUpdate();
         if(rst>0)
             System.out.println("Update successful");
         else
             System.out.println("Update unsuccessful");
         conn.close();
    }
   public void DeleteClass(Student stu) throws ClassNotFoundException,SQLException{
        getConnection();
       
        String id=stu.getCourseid();
        String sql="delete from class where  courseid=?";
        pst=conn.prepareStatement(sql);
        
        pst.setString(1,id);
        System.out.println(id);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Successful");
        }
        else
        {
            System.out.println("unsuccessful");
        }
    }
   
   public Student getAllDataByCourseId(String courseid)throws ClassNotFoundException,SQLException{
        getConnection();
        
        String sql="select startdate,enddate from class where  courseid=?";
        pst=conn.prepareStatement(sql);
        
        pst.setString(1,courseid);
        result=pst.executeQuery();
        Student stu=new Student();
        while(result.next()){
            stu.setStartdate(result.getString(1));
            stu.setEnddate(result.getString(2));
           
        }
        conn.close();
        return stu;
       }
       
    public void PaymentInsert(Student stu) throws ClassNotFoundException, SQLException
{

String paymentdate=stu.getPaymentdate();
String sid=stu.getStudentid();
String courseid=stu.getCourseid();
String fee=stu.getFees();
getConnection();
String sql="insert into payment(date,studentid,classid,fees) values(?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
	
		
		ps.setString(1,paymentdate);
                ps.setString(2,sid);
		ps.setString(3,courseid);
                ps.setString(4,fee);
		int rst=ps.executeUpdate();
		if(rst>0){
			System.out.println("Insert successful");
		}
			else{
				System.out.println("Insert unsuccessful");
			}
		
		//execute a query
		
		//step7 close	the connection
		conn.close();

   }
    
     public String[] getStuIdListFromPayment(String classid) throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
        String query = "select studentid from payment where classid=?";
        pst = conn.prepareStatement(query);
        pst.setString(1, classid);
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
        String[] clid = list.toArray(new String[list.size()]);
        conn.close();
        return clid;
    }
     
     public Student getAllDataByStudentIdClassIdFromPayemnt(String studentid,String claid)throws ClassNotFoundException,SQLException{
        getConnection();
        String id=studentid;
        String sql="select date,fees from payment where studentid=? and classid=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,id);
        pst.setString(2,claid);
        result=pst.executeQuery();
        Student stu=new Student();
        while(result.next()){
            stu.setDate(result.getString(1));
            stu.setFees(result.getString(2));
           
        }
        conn.close();
        return stu;
    } 
     
     public void UpdatePayment(Student stu) throws ClassNotFoundException,SQLException{
        
        String paymentdate=stu.getDate();
        String fee=stu.getFees();
        
        
        getConnection();
        String sql="update payment set date=?,fees=? where  studentid=?";
        PreparedStatement pp=conn.prepareStatement(sql);
        pp.setString(1, paymentdate);
        pp.setString(2,fee);
        
        
         int rst=pp.executeUpdate();
         if(rst>0)
             System.out.println("Update successful");
         else
             System.out.println("Update unsuccessful");
         conn.close();
    }
     
      public String[] getClassIdFromPayment() throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
    String  query = "select classid from payment";
        pst = conn.prepareStatement(query);
       
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
       String[] id = list.toArray(new String[list.size()]);
        conn.close();
        return id;
    }
      
      
       public void DeletePayment(Student stu) throws ClassNotFoundException,SQLException{
        getConnection();
        String stuid=stu.getStudentid();
        String classid=stu.getClassid();
        String paymentdate=stu.getDate();
        String fee=stu.getFees();
        String sql="delete from payment where studentid=? and classid=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,stuid);
        pst.setString(2,classid);
        System.out.println(stuid+" "+classid);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Successful");
        }
        else
        {
            System.out.println("unsuccessful");
        }
    }
       
       public void RegisterCertificate(Student stu) throws ClassNotFoundException, SQLException
{

String studentid=stu.getStudentid();
String classid=stu.getClassid();
String date=stu.getDate();
String issue=stu.getIssue();

getConnection();
String sql="insert into certificate(studentid,classid,date,isissue) values(?,?,?,?)";
		PreparedStatement pst=conn.prepareStatement(sql);
	
		
		pst.setString(1,studentid);
                pst.setString(2,classid);
		pst.setString(3,date);
                pst.setString(4,issue);
               
		int rst=pst.executeUpdate();
		if(rst>0){
			System.out.println("Insert successful");
		}
			else{
				System.out.println("Insert unsuccessful");
			}
		
		//execute a query
		
		//step7 close	the connection
		conn.close();

   }
       
        public String[] getAllClassIdFromCertificate() throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
    String  query = "select classid from certificate";
        pst = conn.prepareStatement(query);
       
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
       String[] id = list.toArray(new String[list.size()]);
        conn.close();
        return id;
    }
    public String[] getStuIdListFromCertificate(String classid) throws ClassNotFoundException, SQLException {
        getConnection();
        ArrayList<String> list = new ArrayList<String>();
        String query = "select studentid from certificate where classid=?";
        pst = conn.prepareStatement(query);
        pst.setString(1, classid);
        result = pst.executeQuery();
        while (result.next()) {
            list.add(result.getString(1));
        }
        String[] clid = list.toArray(new String[list.size()]);
        conn.close();
        return clid;
    }  
    
    public Student getAllDataByStudentIdClassIdFromCertificate(String studentid,String claid)throws ClassNotFoundException,SQLException{
        getConnection();
        String id=studentid;
        String sql="select date from certificate where studentid=? and classid=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,id);
        pst.setString(2,claid);
        result=pst.executeQuery();
        Student stu=new Student();
        while(result.next()){
            stu.setDate(result.getString(1));
           
           
        }
        conn.close();
        return stu;
    } 
    
    
    public void UpdateCertificate(Student stu) throws ClassNotFoundException,SQLException{
        String classid=stu.getClassid();
        String studentid=stu.getStudentid();
        String date=stu.getDate();
        String issue=stu.getIssue();
        
        
        getConnection();
        String sql="update certificate set date=?,isissue=? where  studentid=?";
        PreparedStatement pp=conn.prepareStatement(sql);
        pp.setString(1,date);
        pp.setString(2,issue);
        pp.setString(3,studentid);
        
        
         int rst=pp.executeUpdate();
         if(rst>0)
             System.out.println("Update successful");
         else
             System.out.println("Update unsuccessful");
         conn.close();
    }
public void DeleteCertificate(Student stu) throws ClassNotFoundException,SQLException{
        getConnection();
        String stuid=stu.getStudentid();
        String classid=stu.getClassid();
        
        String sql="delete from certificate where studentid=? and classid=?";
        pst=conn.prepareStatement(sql);
        pst.setString(1,stuid);
        pst.setString(2,classid);
        System.out.println(stuid+" "+classid);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Successful");
        }
        else
        {
            System.out.println("unsuccessful");
        }
    }
} 