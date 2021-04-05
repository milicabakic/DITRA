package actions;

public class ActionManager {
	
	private Add add;
	private Delete delete;
	private Update update;
	private FilterSort sort;
	private Relate relate;
	private CountAverage countAaverage;
	private Search search;
	
	public ActionManager(){
		initialiseActions();
	}

	private void initialiseActions() {	
		this.add = new Add();
		this.delete = new Delete();
		this.update = new Update();
		this.sort = new FilterSort();
		this.relate = new Relate();
		this.countAaverage = new CountAverage();
		this.search = new Search();
	}
	
	public Search getSearch() {
		return search;
	}
	
	public void setSearch(Search search) {
		this.search = search;
	}

	public Relate getRelate() {
		return relate;
	}
	
	public void setRelate(Relate relate) {
		this.relate = relate;
	}
	
	public Add getAdd() {
		return add;
	}

	public void setAdd(Add add) {
		this.add = add;
	}

	public Delete getDelete() {
		return delete;
	}

	public void setDelete(Delete delete) {
		this.delete = delete;
	}
	
	public FilterSort getSort() {
		return sort;
	}
	public void setSort(FilterSort sort) {
		this.sort = sort;
	}
	
	public Update getUpdate() {
		return update;
	}
	
	public void setUpdate(Update update) {
		this.update = update;
	}
	
	public CountAverage getCountAaverage() {
		return countAaverage;
	}
	
	public void setCountAaverage(CountAverage countAaverage) {
		this.countAaverage = countAaverage;
	}
}
