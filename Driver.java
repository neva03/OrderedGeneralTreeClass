public class Driver {
    public static void main(String[] args) {
        OrderedGeneralTree<String> tree = new OrderedGeneralTree();
        Position<String> a = tree.addRoot("A");
        Position<String> b = tree.addChild(a, "B");
        Position<String> c = tree.addChild(a,"C");
        Position<String> d = tree.addChild(a,"D");
        Position<String> e = tree.addChild(b,"E");
        Position<String> f = tree.addChild(b,"F");
        Position<String> g = tree.addChild(d,"G");
        
        tree.displayTree();
        //testing all methods
        System.out.println("size of tree: " + tree.size());
        System.out.println("root of the tree: " + tree.root().getElement());
        System.out.println("parent of G : "+ tree.parent(g).getElement());
        System.out.print("children of b: ");
        for(Position<String> n: tree.children(b)){
            System.out.print(n.getElement() + " ");            
        }
        System.out.println("");
        
        System.out.println("after removing " +tree.remove(b)+" from the tree, the new tree : ");
        tree.displayTree();
        
        System.out.println("new size: "+tree.size());
    }
   
}
