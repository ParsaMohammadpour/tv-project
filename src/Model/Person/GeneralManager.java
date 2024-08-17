package Model.Person;

import Model.Channel.Channel;

import Model.Request.Request;

import Model.Program.Program;

import java.io.Serializable;
import java.util.ArrayList;

public class GeneralManager extends Manager implements Serializable {
    private ArrayList<Manager>managers;
    private ArrayList<Channel> channels;

    public GeneralManager(String name,String lastname,String username,String password){
        super(name,lastname,username,password,null);
    }

    public GeneralManager(String name,String username,String password){
        super(name,null,username,password,null);
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    @Override
    public Channel getChannel() throws Exception{
        throw new Exception("GENERALMANAGER DOESNT HAVE A CHANNEL");
    }

    public Channel[] getChannelsInChannelArray(){
        return (Channel[])this.channels.toArray();
    }

    public void addChannel(Channel channel){
        this.channels.add(channel);
    }

    public Channel getChannelOfChannels(String name){
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i).getName().equals(name)){
                return this.channels.get(i);
            }
        }
        return null;
    }

    public void addChannel(ArrayList<Channel>channels){
        if(channels != null){
            this.channels.addAll(channels);
        }
    }

    public void addChannel(Channel[]channels){
        for (int i = 0; i < channels.length; i++) {
            if(channels[i] != null){
                this.channels.add(channels[i]);
            }
        }
    }
    
    public void AcceptRequest(Request request)throws Exception{
        for (int i = 0; i < this.channels.size(); i++) {
            for (int j = 0; j < channels.get(i).getRequests().size(); j++) {
                if(this.channels.get(i) == null){
                    break;
                }
                if(this.channels.get(i).getRequests().get(j) == null){
                    continue;
                }
                if(this.channels.get(i).getRequests().get(j).equals(request)){
                    this.channels.get(i).acceptRequest(request);
                }
            }
        }
    }

    public void RejectRequest(Request request){
        for (int i = 0; i < this.channels.size(); i++) {
            for (int j = 0; j < channels.get(i).getRequests().size(); j++) {
                if(this.channels.get(i) == null){
                    break;
                }
                if(this.channels.get(i).getRequests().get(j) == null){
                    continue;
                }
                if(this.channels.get(i).getRequests().get(j).equals(request)){
                    this.channels.get(i).rejectRequest(request);
                }
            }
        }
    }

    public void removeRejectedREquest(Channel channel){
        for (int i = 0; i < this.channels.size(); i++) {
            if((this.channels.get(i) != null)&&(this.channels.get(i).equals(channel))){
                this.channels.get(i).removeRejectedRequest();
                return;
            }
        }
    }

    public void removeallRejectedRequest(){
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i) != null){
                this.channels.get(i).removeRejectedRequest();
            }
        }
    }

    public void removeAllDoneRequest(){
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i) != null){
                this.channels.get(i).removeDoneRequest();
            }
        }
    }

    public void removeDonrRequest(Channel channel){
        for (int i = 0; i < this.channels.size(); i++) {
            if((this.channels.get(i) != null)&&(this.channels.get(i).equals(channel))){
                this.channels.get(i).removeDoneRequest();
                return;
            }
        }
    }

    public void removeProgramFromAllChannel(Program program){
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i)== null){
                continue;
            }
            else{
                this.channels.get(i).removeProgram(program);
            }
        }
    }

    public Channel getChannel(String name){
        for (int i = 0; i < this.channels.size(); i++) {
            if (this.channels.get(i).getName().equals(name))return this.channels.get(i);
        }
        return null;
    }

    public Channel getChannel(Channel channel){
        if(getChannel(channel.getName()) == null){
            return null;
        }
        return getChannel(channel.getName());
    }

    public void removeProgramFromOneChannel(Channel channel, Program program){
        if(getChannel(channel) == null){
            return;
        }
        getChannel(channel).removeProgram(program);
    }

    public void setManagerForChannel(Manager manager,Channel channel)throws Exception{
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i).equals(channel)){
                if(this.channels.get(i).getManager() == null){
                    this.channels.get(i).setManager(manager);
                    return;
                }
                else {
                    Exception exception=new Exception("THIS CHANNEL HAS MANAGER!");
                    throw exception;
                }
            }
        }
    }

    public void addManager(Manager manager)throws Exception{
        for (int i = 0; i < this.managers.size(); i++) {
            if(this.managers.get(i).equals(manager)){
                Exception exception =new Exception("THIS MANAGER IS ALREADY EXIST!");
                throw exception;
            }
        }
        this.managers.add(manager);
    }
}