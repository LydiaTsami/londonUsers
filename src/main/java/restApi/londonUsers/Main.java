package restApi.londonUsers;

class Main 
{
    public static void main(String[] args)
    {
        //Default port is 4567.Change the port of the embedded server by removing the un-commenting the next line.
//      port(8080);
    	new UserController(new UserService());

    }
}