





import com.sun.javafx.geom.Edge;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.Random;
public class MyAVLTreeSetLab10<T extends Comparable<T>> extends Pane {
    private static final int Circle_Radius = 35;

    private MyNode root;

    private class MyNode {
        public T data;
        public MyNode left, right, parent;
        public Circle circle;
        public Line edge;
        public Text label;
        public int height;

        public MyNode( T inData) {
            data = inData;
        }



        public void updateHeight(){
            int leftHeight = -1;
            if (this.left != null){
                leftHeight = this.left.height;
            }
            int rightHeight = -1;
            if (this.right != null){
                rightHeight = this.right.height;
            }
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }

        /////IS CALLED BY A PARENT
        public void replaceChild(MyNode currentChild, MyNode newChild) {
            if (left == currentChild) {
                this.left = newChild;

            }
            else if (right == currentChild){
                this.right = newChild;
            }
            if(newChild != null){
                newChild.parent = this;
            }
            this.updateHeight();







            currentChild.edge.startXProperty().unbind();
            currentChild.edge.startYProperty().unbind();
            newChild.edge.startXProperty().bind(this.circle.centerXProperty());
            newChild.edge.startYProperty().bind(this.circle.centerYProperty());


        }

        public int getBalance() {
            int leftHeight = -1;
            if(this.left != null){
                leftHeight = this.left.height;
            }
            int rightHeight = -1;
            if(this.right != null){
                rightHeight = this.right.height;
            }
            return (leftHeight) - (rightHeight);
        }
    }

    private void rotateRight(MyNode node){
        MyNode leftRightChild = node.left.right;
        if(node.parent != null){


            node.parent.replaceChild(node, node.left);





        }
        else{
            root.circle.setStroke(Color.BLACK);



            root = node.left;
            root.parent = null;



            root.circle.setStroke(Color.RED);
            node.left.edge.startXProperty().unbind();
            node.left.edge.startYProperty().unbind();
            getChildren().remove(node.left.edge);



        }
        node.left.right = node;




        if(node.edge != null){
            node.edge.startXProperty().unbind();
            node.edge.startYProperty().unbind();
        }
        else{
            Line newEdge = new Line();
            node.edge = newEdge;
            newEdge.startXProperty().bind(node.circle.centerXProperty());
            newEdge.startYProperty().bind(node.circle.centerYProperty());
            newEdge.endXProperty().bind(node.parent.circle.centerXProperty());
            newEdge.endYProperty().bind(node.parent.circle.centerYProperty());
            this.getChildren().add(newEdge);
            newEdge.toBack();
        }



        if(node != null){
            node.parent = node.left;
        }
        node.left.updateHeight();
        node.left = leftRightChild;
        if(leftRightChild != null){
            leftRightChild.parent = node;


            //THESE TWO LINES ARE SUS
            leftRightChild.edge.startXProperty().unbind();
            leftRightChild.edge.startYProperty().unbind();
            leftRightChild.edge.endXProperty().unbind();
            leftRightChild.edge.endYProperty().unbind();
            leftRightChild.edge.endXProperty().bind(leftRightChild.parent.circle.centerXProperty());
            leftRightChild.edge.endYProperty().bind(leftRightChild.parent.circle.centerYProperty());
        }
        node.updateHeight();
    }
    private void rotateLeft(MyNode node){
        MyNode rightLeftChild = node.right.left;
        if(node.parent != null){


            node.parent.replaceChild(node, node.right);





        }
        else{
            root.circle.setStroke(Color.BLACK);



            root = node.right;
            root.parent = null;


            root.circle.setStroke(Color.RED);
            node.right.edge.startXProperty().unbind();
            node.right.edge.startYProperty().unbind();
            getChildren().remove(node.right.edge);
            //node.parent = root;

            //EXP


        }
        node.right.left = node;








        if(node != null){
            node.parent = node.right;
        }
        if(node.edge != null){
            node.edge.startXProperty().unbind();
            node.edge.startYProperty().unbind();
        }
        //////Error
        else{
            System.out.println((node.parent == null) + " " + node.data);
            Line newEdge = new Line();
            node.edge = newEdge;
            newEdge.startXProperty().bind(node.circle.centerXProperty());
            newEdge.startYProperty().bind(node.circle.centerYProperty());
            newEdge.endXProperty().bind(node.parent.circle.centerXProperty());
            newEdge.endYProperty().bind(node.parent.circle.centerYProperty());
            this.getChildren().add(newEdge);
            newEdge.toBack();
        }
        node.right.updateHeight();
        node.right = rightLeftChild;
        if(rightLeftChild != null){
            rightLeftChild.parent = node;


            //THESE TWO LINES ARE SUS
            rightLeftChild.edge.startXProperty().unbind();
            rightLeftChild.edge.startYProperty().unbind();
            rightLeftChild.edge.endXProperty().unbind();
            rightLeftChild.edge.endYProperty().unbind();
            rightLeftChild.edge.endXProperty().bind(rightLeftChild.parent.circle.centerXProperty());
            rightLeftChild.edge.endYProperty().bind(rightLeftChild.parent.circle.centerYProperty());
        }
        node.updateHeight();

    }
    public boolean add(T addMe){
        MyNode newNode = new MyNode(addMe);
        return AVLAddHelper(newNode);

    }

    /// Check for null?
    private boolean AVLAddHelper(MyNode node) {
        if(root == null){
            root = node;
            node.parent = null;
            drawShapes(node);
            return true;
        }
        MyNode cur = root;
        while(cur != null){
            if(node.data.compareTo(cur.data) < 0){
                if(cur.left == null){
                    cur.left = node;
                    node.parent = cur;
                    drawShapes(node);
                    cur = null;
                }
                else{
                    cur = cur.left;
                }
            }
            else{
                if(cur.right == null){
                    cur.right = node;
                    node.parent = cur;
                    drawShapes(node);
                    cur = null;
                }
                else{
                    cur = cur.right;
                }
            }

        }
        node = node.parent;
        while(node != null){
            rebalance(node);
            node = node.parent;

        }
        return true;
    }

    private void rebalance(MyNode node) {
        node.updateHeight();
        if(node.getBalance() == -2){
            if(node.right.getBalance() == 1){
                rotateRight(node.right);
            }
            rotateLeft(node);
        }
        else if(node.getBalance() == 2){
            if(node.left.getBalance() == -1){
                rotateLeft(node.left);
            }
            rotateRight(node);
        }
    }

    public String toString() {
        return "[" + (toString(root, "")).substring(0, (toString(root, "")).length() - 2 )+ "]";
    }
    /**
     * Using recursive method to print binary tree in pre-order
     */
    public String toString(MyNode node, String result) {
        if (node == null)
            return result;
        result = toString(node.left, result);
        result = toString(node.right, result);
        result += node.data;
        result += ", ";
        return result;

    }
    public void drawShapes(MyNode node) {
        Text label = new Text(node.data.toString());
        label.setFont(new Font(16));
        Random rand = new Random();
        Circle circle = new Circle();
        // Setting the circles coordinates to random numbers within the scene
        circle.setCenterX(rand.nextInt(Lab10.SCENE_WIDTH));
        circle.setCenterY(rand.nextInt(Lab10.SCENE_HEIGHT));
        circle.setRadius(35);
        circle.setFill(Color.WHITE);
        double textWidth = label.getLayoutBounds().getWidth();
        double textHeight = label.getLayoutBounds().getHeight();
        // Binding the circle with text placed in the middle
        label.xProperty().bind(circle.centerXProperty().subtract(textWidth / 2));
        // For the perfect placement in the middle the text needs to be 5 pixels higher
        label.yProperty().bind(circle.centerYProperty().add((textHeight / 2) - 5));
        // The root node has red stroke, others have black
        if (node.equals(root)) {
            circle.setStroke(Color.RED);
        } else {
            circle.setStroke(Color.BLACK);
        }
        Line edge = new Line();
        // Binding the edge if the node is not root
        if (node.parent != null){
            edge.startXProperty().bind(node.parent.circle.centerXProperty());
            edge.startYProperty().bind(node.parent.circle.centerYProperty());
            edge.endXProperty().bind(circle.centerXProperty());
            edge.endYProperty().bind(circle.centerYProperty());
        }
        this.getChildren().add(edge);
        edge.toBack();
        // Moving the circles when they are dragged
        circle.setOnMouseDragged(evt -> {
            double mouseX = evt.getX(),mouseY = evt.getY();
            circle.setCenterX(mouseX);
            circle.setCenterY(mouseY);
        });
        // Updating each node's fields
        node.circle = circle;
        node.label = label;
        if(node != root){
            node.edge = edge;
        }
        this.getChildren().addAll(circle, label);
    }

}







