package com.indix.dbupdater.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.csvreader.CsvReader;
import com.indix.dbupdater.model.ProductDetails;
import com.indix.dbupdater.model.ProductVo;
import com.indix.dbupdater.model.SellerInfo;
import com.indix.dbupdater.model.StoreDetails;
import com.indix.dbupdater.repository.ProductRepository;

@Component
public class CSVFileReader {

	@Autowired
	private ProductRepository productRepository;

	public void writeCSVFolderToMongo(String folderName) {

		if(StringUtils.isEmpty(folderName))
			return;

		File[] files = new File(folderName).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isFile()) {
					return file.getName().contains(".csv");
				}
				return false;
			}
		});
		for (int i = 0; i < files.length; i++) {
			
			writeCSVFileToMongo(files[i].getAbsolutePath(),false);
		}

	}

	public boolean writeCSVFileToMongo(String fileName,boolean considerHader){
		try {
			CsvReader csvReader = new CsvReader(new FileReader(fileName));
			while (csvReader.readRecord()) {
				if(considerHader)csvReader.skipRecord();

				String price = csvReader.get(7);
				String timeStamp = csvReader.get(6);
				String storeId = csvReader.get(4);
				String categoryld = csvReader.get(3);
				String upcs = csvReader.get(2);
				String seller=csvReader.get(5);
				String title=csvReader.get(1);
				String productId=csvReader.get(0);
				
				
				
				ProductVo provo = new ProductVo();
				provo.setPid(productId);
				provo.setTitle(title);
				provo.setUpcs(upcs);
				provo.setCategoryld(isNotEmpty(categoryld)?Double.valueOf(categoryld).longValue():0);
				provo.setStoreld(isNotEmpty(storeId)?Double.valueOf(storeId).longValue():0);
				provo.setSeller(seller);
				provo.setPrice(isNotEmpty(price)?Double.valueOf(price).doubleValue():0);
				
				ProductDetails productInfo = new ProductDetails();
				productInfo.setProductId(productId);
				productInfo.setTitle(csvReader.get(1));
				productInfo.setUpcs(upcs);
				productInfo.setCategoryId(isNotEmpty(categoryld)?Double.valueOf(categoryld).longValue():0);
				SellerInfo sinfo = new SellerInfo();
				sinfo.setTimestamp(isNotEmpty(timeStamp)?Double.valueOf(timeStamp).longValue():0);
				sinfo.setPrice(isNotEmpty(price)?Double.valueOf(price).doubleValue():0);
				sinfo.setSellerName(csvReader.get(5));
				StoreDetails storeDetails = new StoreDetails();
				List<SellerInfo> sellerInfolist = new ArrayList<SellerInfo>();
				sellerInfolist.add(sinfo);
				storeDetails.setSellerInfoList(sellerInfolist);
				storeDetails.setStoreId(isNotEmpty(storeId)?Double.valueOf(storeId).longValue():0);
				Set<StoreDetails> storeSet = new HashSet<StoreDetails>();
				storeSet.add(storeDetails);
				productInfo.setStoreDetailsList(storeSet);
				System.out.println(productInfo);
//read and compare 67b817b7d43370130115d9743d72aaff 67b817b7d43370130115d9743d72aaff 1413577316924 1459290111414
				ProductDetails pd =  productRepository.findOne(productInfo.getProductId());
				//mergeProductInfo(pd, productInfo);
				ProductDetails updateddetails = updetails(pd, provo);
				productRepository.save(updateddetails);

			}
			csvReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}catch (NumberFormatException e) {
			System.out.println("Number format exception");
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static boolean isNotEmpty(String value) {
		return value != null && value.length() > 0;
	}
	
	public ProductDetails createNewObject(ProductVo  newInfo){
		ProductDetails pd = new ProductDetails();
		pd.setProductId(newInfo.getPid());
		pd.setTitle(newInfo.getTitle());
		pd.setUpcs(newInfo.getUpcs());
		pd.setCategoryId(newInfo.getCategoryld());
		List<SellerInfo> sellerInfo = new ArrayList<SellerInfo>();
		sellerInfo.add(new SellerInfo(newInfo.getSeller(),newInfo.getPrice(),newInfo.getTimestamp()));
		Set<StoreDetails> storeDetailsSet = new HashSet<StoreDetails>();
		StoreDetails sd = new StoreDetails();
		sd.setStoreId(newInfo.getStoreld());
		sd.setSellerInfoList(sellerInfo);;
		storeDetailsSet.add(sd);
		return pd;
	}
	public ProductDetails updetails(ProductDetails savedInfo,ProductVo  newInfo){
		if(savedInfo==null || StringUtils.isEmpty(savedInfo.getProductId())){
			return createNewObject(newInfo);
		}

		Set<StoreDetails> storedetailsSet = null;
		StoreDetails storeDetail = null;
	if( savedInfo.getProductId().equals(newInfo.getPid())){
		storedetailsSet = savedInfo.getStoreDetailsSet();
			if(storedetailsSet!= null  && storedetailsSet.contains(newInfo.getStoreld())){
				Iterator<StoreDetails> setI = storedetailsSet.iterator();
				while(setI.hasNext()){
					StoreDetails temp = setI.next();
					if(temp.getStoreId() == newInfo.getStoreld()){
						storeDetail = temp;
					}
				}
			}
			if(storeDetail == null){
				storeDetail = new StoreDetails();
			}
			SellerInfo sellerInfor = null;
			List<SellerInfo> sellerList = storeDetail.getSellerInfoList();
			if(sellerList!=null && sellerList.contains(newInfo.getSeller())){
				for (SellerInfo temp : storeDetail.getSellerInfoList()){
					if(temp.getSellerName().equals(newInfo.getSeller())){
						sellerInfor = temp;
						if(sellerInfor.getTimestamp() < newInfo.getTimestamp()){
							sellerInfor = new SellerInfo(newInfo.getSeller(),newInfo.getPrice(),newInfo.getTimestamp());
						}
						break;
					}
				}
			}
			if(sellerInfor == null){
				sellerInfor = new SellerInfo(newInfo.getSeller(),newInfo.getPrice(),newInfo.getTimestamp());
			}
			storeDetail.addSeller(sellerInfor);
			//ProductDetails f
			savedInfo.setStoreDetailsList(storedetailsSet);
		}
		return savedInfo;
	}
	/*public void mergeProductInfo(ProductDetails savedInfo,ProductDetails newInfo) throws IllegalArgumentException, IllegalAccessException{
		if(savedInfo!=null && savedInfo.getPid().equals(newInfo.getPid())){
			Field[] fields = savedInfo.getClass().getDeclaredFields();
			for (int i=0; i<fields.length ;i++)
			{
				fields[i].setAccessible(Boolean.TRUE);
				String element = fields[i].getName();
				if (element.equals("price")) {
					long time = (Long) fields[i].get(savedInfo);
					if (time < newInfo.getTimestamp())
						savedInfo.setTimestamp(newInfo.getTimestamp());
					else{
						
					}
				}else{
					
				}
			}
		}
		
	}*/

}
