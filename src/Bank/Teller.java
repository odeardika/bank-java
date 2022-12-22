package Bank;

public class Teller extends Person{
    String id;
    Teller(){
        System.out.println("Tidak Ada Data Teller");
    }
    Teller(String id, String name, String username, String password){
        super.name =name;
        super.username = username;
        setPassword(password);
        this.id = id;
    }


}
