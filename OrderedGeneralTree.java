import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderedGeneralTree<E> extends AbstractTree<E> implements Tree<E> {
    //---------------- nested Node class ----------------
  /** Nested static class for a binary tree node. */
    private static class Node<E> implements Position<E>{
        private E element; // an element stored at this node
        private Node<E> parent; // a reference to the parent node (if any)
        private Node<E> firstChild; // a refeerene to the firstChild
        private Node<E> nextSibling; // a reference to the next sibling 
        
        /*
        constructors a node with the given element and neighbors
        */
        public Node(E e, Node<E>p, Node<E>fc,Node<E> ns ){
            element=e;
            parent=p;
            firstChild =fc;
            nextSibling =ns ;
        }

        /**
         * @return the element
         */
        public E getElement() {
            return element;
        }

        /**
         * @param element the element to set
         */
        public void setElement(E element) {
            this.element = element;
        }

        /**
         * @return the parent
         */
        public Node<E> getParent() {
            return parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        /**
         * @return the firstChild
         */
        public Node<E> getFirstChild() {
            return firstChild;
        }

        /**
         * @param firstChild the firstChild to set
         */
        public void setFirstChild(Node<E> firstChild) {
            this.firstChild = firstChild;
        }

        /**
         * @return the nextSibling
         */
        public Node<E> getNextSibling() {
            return nextSibling;
        }

        /**
         * @param nextSibling the nextSibling to set
         */
        public void setNextSibling(Node<E> nextSibling) {
            this.nextSibling = nextSibling;
        }
    } //ended of nested Node class
    
    /*
    factory function t create a new node storing element e
    */
    protected Node<E> createNode(E e,Node<E>p,Node<E>fc,Node<E> ns){
        return new Node<E>(e,p,fc,ns);
    }
    
    //OrderedGeneralTree instance variables
    protected Node<E> root =null; // root of the general tree
    private int size =0; // number of nodes in the tree
    
    /*
    constructs an empty general tree
    */
    public OrderedGeneralTree(){
        
    }
    
    /*
    checks if the position p is valid
    */
    protected Node<E> validate(Position<E> p)throws IllegalArgumentException{
        if(! (p instanceof Node))
            throw new IllegalArgumentException("not valid position type");
        Node<E> node =(Node<E>)p; //casting
        if(node.getParent()==node){
            throw new IllegalArgumentException("p is no longer in the tree");
        }
        return node;
    }
    
    /*
    returns number of node
    */
    public int size(){
        return size;
    }
    
    /*
    return the root position of the tree
    */
    @Override
    public Position<E> root() {        
        return root;
    }

    /*
    returns the position of p's parent
    */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    /*
    returns the list of the children
    */
    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
        List<Position<E>> snapshot = new ArrayList<>(2); //max capacity of 2
        Node<E> nodeP = validate(p);
        Node<E> child =nodeP.getFirstChild();
        if (child != null) {
            snapshot.add(child);
            while (child.nextSibling != null) {
                snapshot.add(child.nextSibling);
                child = child.nextSibling;
            }
        }
        return snapshot;
    }
    
    /*
    creates a new root node with element e, and returns the node as a position.
    If the tree is non-empty this method should return null
    */
    public Position<E> addRoot(E e){
        if(!isEmpty())
            return null;
        root =createNode(e,null,null,null);
        size = 1; 
        return root; 
    }
    
    /*
    Creates a new child node of position p. 
    The child nodes must be linked in the order they are added.
    The newly made child node is returned as a position
    */
    public Position<E> addChild(Position<E> p, E e){
        Node<E> node = validate(p);
        Node<E> child = node.firstChild;
        if(child != null){
            while(child.nextSibling != null){
                child =child.nextSibling;
            }
            child.nextSibling =createNode(e,node,null,null);
            size++;
            return child.nextSibling;
        }else{
            child = createNode(e,node,null,null);
            node.setFirstChild(child);
            size++;
            return child;
        }        
    }
    
    /*
    Removes the position p and returns the element in p
    If p has children, they become the children of p’s parent, appearing in the same order as p appeared
    */
    public E remove(Position p){
        Node<E> node = validate(p);
        if(node == root){
            return null;
        }
        Node<E> parent = node.getParent();
        Node<E> child= node.getFirstChild();
        Node<E> childrenOfParent =parent.getFirstChild(); //this will be the last child before node child.
        Node<E> nextSiblingOfNode =node.getNextSibling();
        if(childrenOfParent == node){
            if(child != null){
                parent.setFirstChild(child);
            }else{
                parent.setFirstChild(nextSiblingOfNode);
            }
            
        }else {
            if (childrenOfParent != null) {
                while (childrenOfParent.getNextSibling() != node) {
                    childrenOfParent = childrenOfParent.getNextSibling();
                }
            }
            childrenOfParent.setNextSibling(child);
        }
        if(child==null){
            childrenOfParent.setNextSibling(nextSiblingOfNode);
        }else {
            child.setParent(parent);
            if (child != null) {
                while (child.getNextSibling() != null) {
                    child = child.getNextSibling();
                }
                child.setNextSibling(nextSiblingOfNode);
            }
        }
        
        size-=1;
        return node.getElement();
    }
    
    /*
    displays the tree as wanted.
    */
    public void displayTree(){
        if(!isEmpty())
            displayTreeAux(root,0);
    }
    private void displayTreeAux(Position<E> p, int depth){
        Node<E> node =validate(p);
        for(int i =0;i<depth;i++){
            System.out.print(".");
        }
        System.out.println(node.getElement());
        Node<E> sibling  = node.getNextSibling();
        Node<E> child = node.getFirstChild();
        if(child != null){
            displayTreeAux(child,depth+1);
        }
        if(sibling != null){
            displayTreeAux(sibling,depth);
        }        
    }

}
