package ErrorDetection;

public class InheritanceTree {
    public String id;
    public InheritanceTree parent;
    public InheritanceTree(String id, String parentID){
        
    }
    public InheritanceTree(String id, InheritanceTree parent){
        this.id=id;
        this.parent=parent;
    }
}
