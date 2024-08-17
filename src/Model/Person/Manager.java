package Model.Person;

import Model.Channel.Channel;

import Model.Program.Advertisement.Advertisement;
import Model.Program.Entertainment;
import Model.Program.News;
import Model.Request.Request;

import Model.Program.Program;

import java.io.Serializable;


public class Manager extends Person implements Serializable {
    private Channel channel;

    public Manager(String name,String lastname,String username,String password,Channel channel){
        super(name,lastname,username,password);
        setChannel(channel);
    }

    public Manager(String name,String username,String password,Channel channel){
        super(name,null,username,password);
        setChannel(channel);
    }

    private void setChannel(Channel channel){
        this.channel =channel;
    }

    public Channel getChannel()throws Exception {
        return channel;
    }

    public void acceptRequest(Request request)throws Exception{
        this.channel.acceptRequest(request);
    }

    public void rejectRequest(Request request){
        this.channel.rejectRequest(request);
    }

    public void removeRejectedRequest(){
        this.channel.removeRejectedRequest();
    }

    public void removeDoneRequest(){
        this.channel.removeDoneRequest();
    }

    public void removeProgram(Program program){
        this.channel.removeProgram(program);
    }

    private void setPresenterForProgram(News news,Presenter presenter){
        for (int i = 0; i < this.channel.getPrograms().size(); i++) {
            if(this.channel.getPrograms().get(i).equals(news)){
                news.setPresenter(presenter);
                return;
            }
        }
    }

    private void setPresenterForProgram(Entertainment entertainment, Presenter presenter){
        for (int i = 0; i < this.channel.getPrograms().size(); i++) {
            if(this.channel.getPrograms().get(i).equals(entertainment)){
                entertainment.setPresenter(presenter);
                return;
            }
        }
    }

    public void setPresenterForProgram(Program program,Presenter presenter){
        if(program instanceof News){
            setPresenterForProgram((News) program,presenter);
        }
        if(program instanceof Advertisement){
            setPresenterForProgram((Advertisement) program,presenter);
        }
    }
}
