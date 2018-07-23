package cn.xlink.parkinglots.api.page;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResult implements Serializable {
	private int     currentPage;
	private int     pageSize;
	private List<?> list;
	private int     totalCount;
	private int     totalPage;
	private int     prevPage;
	private int     nextPage;

	public PageResult(int currentPage, int pageSize, List<?> list, int totalCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.list = list;
		this.totalCount = totalCount;

		totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		prevPage = currentPage - 1 > 1 ? currentPage - 1 : 1;
		nextPage = currentPage + 1 > totalPage ? totalPage : currentPage + 1;
	}



	public static List<Document> query(MongoDatabase db, Bson bson , String tableName, int skip, int limit) {
		List<Document> newLins = new ArrayList<>();
		Block<Document> saveBlock = new Block<Document>() {
			@Override
			public void apply(final Document document) {
				newLins.add(document);

			}
		};

		//查询
		MongoCollection<Document> collection = db.getCollection(tableName);

		collection.find(bson).skip(skip).limit(limit).forEach(saveBlock);

		return newLins;
	}



	public static int count(DB db, String name){
		DBCollection dbColl = db.getCollection(name);
		int count = dbColl.find().count();
		return count;
	}
}
