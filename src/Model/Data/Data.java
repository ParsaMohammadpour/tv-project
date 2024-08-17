package Model.Data;

import Model.Channel.Channel;

import Model.Person.*;

import Model.Program.Advertisement.*;

import Model.Program.*;

import java.io.Serializable;

import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<User>users;
    private ArrayList<Channel>channels;
    private ArrayList<Program>programs;
    private ArrayList<Manager>managers;
    private GeneralManager generalManager;
    private ArrayList<Presenter>presenters;
    private ArrayList<Company>companies;

    public Data(GeneralManager generalManager){
        setGeneralManager(generalManager);
        setChannels(generalManager.getChannels());
        /*
        outer :
        for (int i = 0; i < this.channels.size(); i++) {
            iner :
            for (int j = 0; j < this.channels.get(i).getPrograms().size(); j++) {
                if(this.channels.get(i).getPrograms().get(i).hasProgram(this.programs)){
                    continue outer;
                }
                this.programs.add(this.channels.get(i).getPrograms().get(j));
            }
        }
        this.managers =generalManager.getManagers();
         */
        users =new ArrayList<>();
        presenters =new ArrayList<>();
        programs =new ArrayList<>();
        managers =new ArrayList<>();
        companies =new ArrayList<>();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setGeneralManager(GeneralManager generalManager) {
        this.generalManager = generalManager;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }

    public void setPresenters(ArrayList<Presenter> presenters) {
        this.presenters = presenters;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Presenter> getPresenters() {
        return presenters;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void registerUser(String name,String lastname,String username,String password1,String password2)throws Exception{
        if(!password1.equals(password2)){
            throw new Exception("PASSWORDS DOESNT MATCHE!");
        }
        for (int i = 0; i < this.users.size(); i++) {
            if(users.get(i).getUsername().equals(username)){
                Exception exception =new Exception("THIS USERNAME ALREADY HAS BEEN TAKEN!");
                throw exception;
            }
        }
        /*for (int i = 0; i < this.getPresenters().size(); i++) {
            if(presenters.get(i).getUsername().equals(username)){
                Exception exception =new Exception("THIS USERNAME ALREADY HAS BEEN TAKEN!");
                throw exception;
            }
        }*/
        for (int i = 0; i < this.managers.size(); i++) {
            if(managers.get(i).getUsername().equals(username)){
                Exception exception =new Exception("THIS USERNAME ALREADY HAS BEEN TAKEN!");
                throw exception;
            }
        }
        if(username.equals(this.generalManager.getUsername())){
            Exception exception =new Exception("THIS USERNAME ALREADY HAS BEEN TAKEN!");
            throw exception;
        }
        if(password1.length()<5){
            Exception exception =new Exception("PASSWORD MUST BE ATLEAST 5 CHARACTER!");
            throw exception;
        }
        User user =new User(name, lastname, username, password1);
        this.users.add(user);
    }

    public void registerManager(String name,String lastname,String username,String password,Channel channel)throws Exception{
        if((name==null)||(lastname==null)||(username==null)||
                (password==null)||(channel==null)){
            Exception exception =new Exception("FILL ALL FIELDS!");
            throw exception;
        }
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).getUsername()==username){
                Exception exception =new Exception("THIS USERNAME ALREADY HAS BEEN TAKEN!");
                throw exception;
            }
            if(this.managers.get(i).getChannel().equals(channel)){
               Exception exception =new Exception("THIS CHANNEL ALREADY HAS BEEN TOOKEN!");
               throw exception;
            }
        }
        if(password.length()<5){
            Exception exception =new Exception("PASSWORD MUST BE ATLEAST 5 CHARACTER!");
            throw exception;
        }
        Manager manager =new Manager(name,lastname,username,password,channel);
        this.managers.add(manager);
        for (int i = 0; i < generalManager.getChannels().size(); i++) {
            if(generalManager.getChannels().get(i).equals(channel)){
                generalManager.getChannels().get(i).setManager(manager);
            }
        }
    }

    public void addPresenter (Presenter presenter)throws Exception{
        if(presenter.getName()==null || presenter.getLastname()==null){
            Exception exception =new Exception("PRESENTER NEED ATLEAST NAME & LASTNAME!");
            throw exception;
        }
        this.presenters.add(presenter);
    }

    public void setPresenterForProgram(String presenterName,String presenterLastname, Program program)throws Exception{
        Presenter presenter =this.getPresenterWithName(presenterName);
        if(presenter==null || program==null){
            Exception e =new Exception("PARAMETERS ARE NULL!");
            throw e;
        }
        if(program instanceof News || program instanceof Advertisement){
            this.generalManager.setPresenterForProgram(program,presenter);
            return;
        }
        Exception e =new Exception("ONLY NEWS & ADVERTISEMENT HAVE PRESENTER!");
        throw e;
    }

    public void vote(String usernameOfUser,String programName,int number)throws  Exception{
        Program program =this.getProgramWithName(programName);
        User user =this.getUserWithUsername(usernameOfUser);
        if(program instanceof Advertisement){
            Exception e= new Exception("ADVERTISEMENT DOSENT HAVE RATES!");
            throw e;
        }
        program.vote(this.getUserWithUsername(usernameOfUser),number);
    }

    private User getUserWithUsername(String username)throws Exception{
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(username)){
                return this.users.get(i);
            }
        }
        Exception e =new Exception("NO SUCH USERNAME!");
        throw e;
    }

    private Program getProgramWithName(String name)throws Exception{
        for (int i = 0; i < this.programs.size(); i++) {
            System.out.println("&*&*&*&*&*&*&*&*");
            System.out.println(this.programs.get(i).getName());
            System.out.println(name);
            if (this.programs.get(i).getName().equals(name)){
                return this.programs.get(i);
            }
        }
        throw new Exception("No Program With This Name");
    }

    public Channel getChannelWithName(String name)throws Exception{
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i).getName().equals(name)){
                return this.channels.get(i);
            }
        }
        Exception exception =new Exception("NO SUCH CHANNEL!");
        throw exception;
    }

    public Manager getManagerWithUsername(String username)throws Exception{
        for (int i = 0; i < this.managers.size(); i++) {
            if (this.managers.get(i).getUsername().equals(username)){
                return this.managers.get(i);
            }
        }
        Exception exception =new Exception("NO SUCH USERNAME IN MANAGERS!");
        throw exception;
    }

    public Presenter getPresenterWithName(String name) throws Exception{
        for (int i = 0; i < this.presenters.size(); i++) {
            if(this.presenters.get(i).getName().equals(name)){
                return this.presenters.get(i);
            }
        }
        throw new Exception("NO SUCH PRESENTER");
    }

    public void setManagerForChannel(String username,String channelName)throws Exception{
        Manager manager =this.getManagerWithUsername(username);
        Channel channel =this.getChannelWithName(channelName);
        if(!channel.getManager().equals(null)){
            Exception exception=new Exception("THIS CHANNEL HAS MANAGER!");
            throw exception;
        }
        channel.setManager(manager);
    }

    public void reSetManagerForProgram(String username,String channelName)throws Exception{
        Channel channel =this.getChannelWithName(channelName);
        Manager manager =this.getManagerWithUsername(username);
        channel.setManager(manager);
    }

    public void changeLastname(String username,String newlastname,String password)throws Exception{
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(password)){
                this.users.get(i).changeLastname(newlastname);
                return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(password)) {
                    this.managers.get(i).changeLastname(newlastname);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        if(this.generalManager.getUsername().equals(username)){
            if(this.generalManager.getPassword().equals(password)){
                this.generalManager.changeLastname(newlastname);
                return;
            }
            else {
                Exception exception =new Exception("WRONG PASSWORD!");
                throw exception;
            }
        }
        Exception exception =new Exception("NO SUCH USERNAME!");
        throw exception;
    }

    public void changeName(String username,String password,String newName)throws Exception{
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(password)){
                    this.users.get(i).chaneName(newName);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(password)) {
                    this.managers.get(i).chaneName(newName);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        if(this.generalManager.getUsername().equals(username)){
            if(this.generalManager.getPassword().equals(password)){
                this.generalManager.chaneName(newName);
                return;
            }
            else {
                Exception exception =new Exception("WRONG PASSWORD!");
                throw exception;
            }
        }
        Exception exception =new Exception("NO SUCH USERNAME!");
        throw exception;
    }

    public void changePassword(String username,String prePassword,String newPassword)throws Exception{
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(prePassword)){
                    this.users.get(i).changePassword(newPassword);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).getUsername().equals(username)){
                if(this.users.get(i).getPassword().equals(prePassword)) {
                    this.managers.get(i).changePassword(newPassword);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        if(this.generalManager.getUsername().equals(username)){
            if(this.generalManager.getPassword().equals(prePassword)){
                this.generalManager.changePassword(newPassword);
                return;
            }
            else {
                Exception exception =new Exception("WRONG PASSWORD!");
                throw exception;
            }
        }
        Exception exception =new Exception("NO SUCH USERNAME!");
        throw exception;
    }

    public void changeUsername(String preUsername,String password,String newUsername)throws Exception{
        int numberOfThisUsername =0;
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(newUsername))
                numberOfThisUsername++;
        }
        for (int i = 0; i <this.managers.size() ; i++) {
            if(this.managers.get(i).getUsername().equals(newUsername))
                numberOfThisUsername++;
        }
        /*for (int i = 0; i < this.presenters.size(); i++) {
            if(this.presenters.get(i).getUsername().equals(newUsername))
                numberOfThisUsername++;
        }*/
        if(this.generalManager.getUsername().equals(newUsername))
            numberOfThisUsername++;
        if(numberOfThisUsername > 0){
            Exception exception =new Exception("THIS NEW USERNAME HAS ALREADY BEEN TAKEN!");
            throw exception;
        }
        for (int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getUsername().equals(preUsername)){
                if(this.users.get(i).getPassword().equals(password)){
                    this.users.get(i).setUsername(newUsername);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).getUsername().equals(preUsername)){
                if(this.users.get(i).getPassword().equals(password)) {
                    this.managers.get(i).setUsername(newUsername);
                    return;
                }
                else{
                    Exception exception =new Exception("WRONG PASSWORD!");
                    throw exception;
                }
            }
        }
        if(this.generalManager.getUsername().equals(preUsername)){
            if(this.generalManager.getPassword().equals(password)){
                this.generalManager.changeLastname(newUsername);
                return;
            }
            else {
                Exception exception =new Exception("WRONG PASSWORD!");
                throw exception;
            }
        }
        Exception exception =new Exception("NO SUCH USERNAME!");
        throw exception;
    }

    public Program getProgram(String name)throws Exception{
        for (int i = 0; i < this.programs.size(); i++) {
            if(this.programs.get(i).getName().equals(name))
                return this.programs.get(i);
        }
        Exception exception =new Exception("NO SUCH PROGRAM WITH THIS NAME!");
        throw exception;
    }

    public void addProgram(Program program,String username) throws Exception{
        Manager manager =this.getManagerWithUsername(username);
        for (int i = 0; i < manager.getChannel().getPrograms().size(); i++) {
            System.out.println("FOR CAMED");
            if ((program instanceof Series)||(manager.getChannel().getPrograms().get(i) instanceof Series)){
                if (program.getStartTime().isLater(manager.getChannel().getPrograms().get(i).getEndTime()) ||
                manager.getChannel().getPrograms().get(i).getStartTime().isLater(program.getEndTime())){
                    System.out.println("CORRECT IF Situation");
                    continue;
                }else {
                    throw new Exception("HAVE TIME CRASH!");
                }
            }
            if (program.getName().equals(manager.getChannel().getPrograms().get(i).getName())){
                throw new Exception("THIS NAME HAS BEEN USED!");
            }
            if (program.getDate().equals(manager.getChannel().getPrograms().get(i).getDate())){
                if (program.hasTimeCrash(manager.getChannel().getPrograms().get(i))){
                    throw new Exception("HAVE TIME CRASH!");
                }
            }
        }
        manager.getChannel().addProgram(program);
        this.programs.add(program);
        System.out.println("Done");
    }

    public void addProgram(Program program,String channelName,boolean isGeneralManager)throws Exception{
        System.out.println("********************************");
        Channel channel=null;
        try {
            System.out.println("********************************");
            channel=this.getChannelWithName(channelName);
            System.out.println("********************************");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
        System.out.println("********************************");
        channel.addProgram(program);
        this.programs.add(program);
        System.out.println("********************************");
    }
}