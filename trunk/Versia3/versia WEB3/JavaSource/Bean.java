import java.util.List;
import java.util.ArrayList;
import javax.faces.model.SelectItem;

public class Bean {
  private List<SelectItem> listaDestino;
  private List<SelectItem> listaOrigem;
   
  public List<SelectItem> getListaDestino() {
    if (listaDestino == null) {
      listaDestino = new ArrayList<SelectItem>();
    }
    return listaDestino;
  }
  public List<SelectItem> getListaOrigem() {
    if (listaOrigem == null) {
      listaOrigem = new ArrayList<SelectItem>();
       
      /* Criei esse objeto apenas a título de exemplo. 
       * Num caso real, você poderia buscar a lista do banco.
       */
      //SelectItem o = ;
      listaOrigem.add(new SelectItem("1", "firrst"));
      listaOrigem.add(new SelectItem("2", "second"));
    }  
    return listaOrigem;
  }
  public void setListaDestino(List<SelectItem> listaDestino) {
    this.listaDestino = listaDestino;
  }
  public void setListaOrigem(List<SelectItem> listaOrigem) {
    this.listaOrigem = listaOrigem;
  }
	public String Save() {
		System.out.println("Bean/Save()");
		return null;
	}
}