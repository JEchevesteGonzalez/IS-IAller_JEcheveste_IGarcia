package exceptions;
public class ResenaAlreadyExistsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public ResenaAlreadyExistsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public ResenaAlreadyExistsException(String s)
  {
    super(s);
  }
}