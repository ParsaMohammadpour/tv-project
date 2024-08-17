package Model.Person;

import Model.Channel.Channel;

import Model.Program.Program;

import java.io.Serializable;
import java.util.ArrayList;

public class User extends Person implements Serializable {
    ArrayList<Channel>channels;

    public User(String name,String lastname,String username,String password){ super(name,lastname,username,password); }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void vote(Channel channel, Program program,int number) throws Exception {
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i) != null){
                if(this.channels.get(i).equals(channel)){
                    channel.vote(program,this,number);
                }
            }
        }
    }

    public void vote(Program program ,int number) throws Exception {
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i) != null){
                this.channels.get(i).vote(program,this,number);
            }
        }
    }

    public ArrayList<Program> getProgramOfChannel(String channelName){
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i)!=null){
                if(this.channels.get(i).getName().equals(channelName)){
                    return this.channels.get(i).getPrograms();
                }
            }
        }
        return null;
    }

    public Program getProgram(String name) throws Exception{
        for (int i = 0; i < this.channels.size(); i++) {
            if(this.channels.get(i) != null){
                for (int j = 0; j < this.channels.get(i).getPrograms().size(); j++) {
                    if(this.channels.get(i).getPrograms().get(j).getName().equals(name)){
                        return this.channels.get(i).getPrograms().get(j);
                    }
                }
            }
        }
        Exception NoSuchProgram =new Exception("NO SUCH PROGRAM WITH THIS NAME");
        return null;
    }
}
