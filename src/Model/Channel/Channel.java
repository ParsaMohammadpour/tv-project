package Model.Channel;

import Model.Program.Advertisement.Advertisement;

import Model.Request.*;

import Model.Person.*;

import Model.Program.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Channel implements Serializable {
    private String name;
    private boolean hasManager;
    private GeneralManager generalManager;
    private Manager manager;
    private ArrayList<Program> programs;
    private Formula formula;
    private ArrayList<Request> requests;

    public Channel(String name,GeneralManager generalManager){
        setName(name);
        setGeneralManager(generalManager);
        setHasManager(false);
        requests =new ArrayList<>();
        programs=new ArrayList<>();
        formula=null;
    }

    public Channel(String name,GeneralManager generalManager,Manager manager){
        this(name,generalManager);
        setManager(manager);
        setHasManager(true);
        requests =new ArrayList<>();
        programs=new ArrayList<>();
    }

    public Channel(String name,GeneralManager generalManager,Manager manager,ArrayList<Program>programs){
        this(name,generalManager,manager);
        setPrograms(programs);
        requests =new ArrayList<>();
    }

    public Channel(String name,GeneralManager generalManager,Manager manager,ArrayList<Program>programs,Formula formula){
        this(name,generalManager,manager,programs);
        setFormula(formula);
        requests =new ArrayList<>();
    }

    public Channel(String name,GeneralManager generalManager,Manager manager,Program[]programs){
        this(name,generalManager,manager);
        setPrograms(programs);
        requests =new ArrayList<>();
    }

    public Channel(String name,GeneralManager generalManager,Manager manager,Program[]programs,Formula formula){
        this(name,generalManager,manager,programs);
        setFormula(formula);
        requests =new ArrayList<>();
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setGeneralManager(GeneralManager generalManager){
        this.generalManager =generalManager;
    }

    private void setHasManager(boolean hasManager){
        this.hasManager =hasManager;
    }

    private void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    public void addRequest(Request[] requests){
        for (int i = 0; i < requests.length; i++) {
            if(requests[i] != null){
                this.requests.add(requests[i]);
            }
        }
    }

    public void addRequest(Request request)throws Exception{
        if(request != null){
            for (int i = 0; i < this.programs.size(); i++) {
                if(this.programs.get(i) != null){
                    if (request.getAdvertisement().hasTimeCrash(this.programs.get(i))){
                        throw new Exception("Has TimeCrash!");
                    }
                }
            }
            this.setPriceOfAdvertisingFurmola();
            this.requests.add(request);
            System.out.println("TA INJA KE OKEYE");
            request.getAdvertisement().setPrice(this.formula.price(request.getAdvertisement(),false));
            System.out.println("E");
        }
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public Request[] getRequestInArray(){
        return (Request[])this.requests.toArray();
    }

    public void setFormula(Formula formula){
        this.formula =formula;
        setPriceOfAdvertisingFurmola();
    }

    private void setPrograms(Program[]programs){
        for (int i = 0; i < programs.length; i++) {
            this.programs.add(programs[i]);
        }
    }

    public Formula getFormula() {
        return formula;
    }

    public Manager getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }

    public Program[] getProgramsInProgramArray(){
        return (Program[]) this.programs.toArray();
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    private void setPriceOfAdvertisingFurmola(){
        if (formula==null)return;
        for (int i = 0; i < this.requests.size(); i++) {
                long price =this.formula.price((Advertisement) this.requests.get(i).getAdvertisement(),
                        this.requests.get(i).getAdvertisement().isBetweenPrograms());
                this.requests.get(i).getAdvertisement().setPrice(price);
        }
    }

    public void addProgram(Program program)throws Exception{
        for (int i = 0; i < programs.size(); i++) {
            if (program.getName().equals(programs.get(i).getName())){throw new Exception("THIS NAME HAS BEEN USED!");}

        }
        this.programs.add(program);
        setPriceOfAdvertisingFurmola();
    }

    public void addProgram(Program[] programs){
        for (int i = 0; i < programs.length; i++) {
            if(programs[i] != null){
                this.programs.add(programs[i]);
            }
        }
        setPriceOfAdvertisingFurmola();
    }

    public void addProgram(ArrayList<Program>programs){
        if(programs != null){
            this.programs.addAll(programs);
        }
        setPriceOfAdvertisingFurmola();
    }

    public boolean isHasManager() {
        return hasManager;
    }

    public void acceptRequest(Request request)throws Exception{
        System.out.println("OMADE1");
        boolean b=false;
        for (int i = 0; i < this.requests.size(); i++) {
            System.out.println("OMADE2");
            System.out.println(requests.get(i).getAdvertisement().getName());
            System.out.println(request.getAdvertisement().getName());
            if((this.requests.get(i).equals(request)) &&(this.requests.get(i).getStatus().equals(Status.PENDING))){
                b=true;
                for (int j = 0; j < this.programs.size(); j++) {
                    if(this.programs.get(i) != null){
                        if(this.programs.get(i).hasTimeCrash(request.getAdvertisement())){
                            request.setStatus(Status.REJECTED);
                            System.out.println("Has Time Crash");
                            throw new Exception("Has Time Crash");
                        }
                    }
                }
                System.out.println(request.getAdvertisement().getPrice());
                if (request.getAdvertisement().getCompany().getMoney()<request.getAdvertisement().getPrice()){
                    request.setStatus(Status.REJECTED);
                    System.out.println("Not Enough Money");
                    throw new Exception("Not Enough Money");
                }
                request.setStatus(Status.ACCEPTED);
                this.programs.add(this.requests.get(i).getAdvertisement());
                request.getAdvertisement().getCompany().addMoney(-request.getAdvertisement().getPrice());
                System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUU");
            }
        }
        System.out.println(b);
    }

    public void rejectRequest(Request request){
        for (int i = 0; i < this.requests.size(); i++) {
            if((this.requests.get(i).equals(request))&&(this.requests.get(i).getStatus().equals(Status.PENDING))){
                request.setStatus(Status.REJECTED);
            }
        }
    }

    public void removeRejectedRequest(){
        for (int i = this.requests.size()-1; i >=0 ; i--) {
            if(this.requests.get(i).getStatus().equals(Status.REJECTED)){
                this.requests.remove(i);
            }
        }
    }

    public void removeDoneRequest(){
        for (int i = this.requests.size()-1; i >= 0; i--) {
            if(this.requests.get(i).getStatus().equals(Status.PENDING)){
                continue;
            }
            else{
                this.requests.remove(i);
            }
        }
    }

    public void removeProgram(Program program){
        for (int i = this.programs.size()-1; i >= 0; i--) {
            if((this.programs.get(i) != null)&&(this.programs.get(i).equals(program))){
                this.programs.remove(i);
                return;
            }
        }
    }

    public void vote(Program program,User user,int number) throws Exception {
        for (int i = 0; i < this.programs.size(); i++) {
            if(this.programs.get(i) != null){
                if(this.programs.get(i).equals(program)){
                    if(program instanceof Movie){
                        ((Movie) program).vote(user,number);
                        return;
                    }
                    if(program instanceof Series){
                        ((Series) program).vote(user,number);
                        return;
                    }
                    if(program instanceof Entertainment){
                        ((Entertainment) program).vote(user,number);
                        return;
                    }
                    if(program instanceof News){
                        ((News) program).vote(user, number);
                        return;
                    }
                }
            }
        }
    }
}
