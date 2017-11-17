/*    */ package de.hybris.platform.customerreview.impl;
/*    */ 
/*    */ import de.hybris.platform.core.model.c2l.LanguageModel;
/*    */ import de.hybris.platform.core.model.product.ProductModel;
/*    */ import de.hybris.platform.core.model.user.UserModel;
/*    */ import de.hybris.platform.customerreview.CustomerReviewService;
/*    */ import de.hybris.platform.customerreview.dao.CustomerReviewDao;
/*    */ import de.hybris.platform.customerreview.jalo.CustomerReview;
/*    */ import de.hybris.platform.customerreview.jalo.CustomerReviewManager;
/*    */ import de.hybris.platform.customerreview.model.CustomerReviewModel;
/*    */ import de.hybris.platform.jalo.product.Product;
/*    */ import de.hybris.platform.jalo.user.User;
/*    */ import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;
/*    */ import de.hybris.platform.servicelayer.model.ModelService;
/*    */ import de.hybris.platform.servicelayer.util.ServicesUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Required;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultCustomerReviewService
/*    */   extends AbstractBusinessService
/*    */   implements CustomerReviewService
/*    */ {	

			/* ============ MODIFIED BY YIFAN WU ============ */
			private List<String> cWordList;

			protected List<String> getCWordList(){
				return this.cWordList;
			}

			@Required
			public void setCWordList(List<String> cWordList){
				this.cWordList = cWordList;
			}



/*    */   private CustomerReviewDao customerReviewDao;
/*    */   
/*    */   protected CustomerReviewDao getCustomerReviewDao()
/*    */   {
/* 41 */     return this.customerReviewDao;
/*    */   }
/*    */   
/*    */   @Required
/*    */   public void setCustomerReviewDao(CustomerReviewDao customerReviewDao)
/*    */   {
/* 47 */     this.customerReviewDao = customerReviewDao;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product)
/*    */   {
				/* ============ MODIFIED BY YIFAN WU ============ */
				if(rating < 0){
					throw new InvalidRatingException("Rating value is invalid");
				}
				for(String i: this.cWordList){
					if(comment.contains(i)){
						throw new InappropriateWordException("Inappropriate word used in the comment");
					}
				}
				
/* 54 */     CustomerReview review = CustomerReviewManager.getInstance().createCustomerReview(rating, headline, comment, 
/* 55 */       (User)getModelService().getSource(user), (Product)getModelService().getSource(product));
/* 56 */     return (CustomerReviewModel)getModelService().get(review);
/*    */   }
/*    */   
/*    */ 
/*    */   public void updateCustomerReview(CustomerReviewModel model, UserModel user, ProductModel product)
/*    */   {
/* 62 */     model.setProduct(product);
/* 63 */     model.setUser(user);
/* 64 */     getModelService().save(model);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<CustomerReviewModel> getAllReviews(ProductModel product)
/*    */   {
/* 70 */     List<CustomerReview> reviews = CustomerReviewManager.getInstance().getAllReviews(
/* 71 */       (Product)getModelService().getSource(product));
/* 72 */     return (List)getModelService().getAll(reviews, new ArrayList());
/*    */   }
/*    */   
/*    */ 
/*    */   public Double getAverageRating(ProductModel product)
/*    */   {
/* 78 */     return CustomerReviewManager.getInstance().getAverageRating((Product)getModelService().getSource(product));
/*    */   }
/*    */   
/*    */ 
/*    */   public Integer getNumberOfReviews(ProductModel product)
/*    */   {
/* 84 */     return CustomerReviewManager.getInstance().getNumberOfReviews((Product)getModelService().getSource(product));
/*    */   }

			/* ============ MODIFIED BY YIFAN WU ============ */
			public Integer getNumberOfReviewsWithinRange(ProductModel product, Double low, Double, high){
				return CustomerReviewManager.getInstance().getNumberOfReviewsWithinRange((Product)getModelService().getSource(product), low, high);
			}
/*    */   
/*    */ 
/*    */   public List<CustomerReviewModel> getReviewsForProduct(ProductModel product)
/*    */   {
/* 90 */     ServicesUtil.validateParameterNotNullStandardMessage("product", product);
/* 91 */     return getCustomerReviewDao().getReviewsForProduct(product);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<CustomerReviewModel> getReviewsForProductAndLanguage(ProductModel product, LanguageModel language)
/*    */   {
/* 97 */     ServicesUtil.validateParameterNotNullStandardMessage("product", product);
/* 98 */     ServicesUtil.validateParameterNotNullStandardMessage("language", language);
/* 99 */     return getCustomerReviewDao().getReviewsForProductAndLanguage(product, language);
/*    */   }
/*    */ }


/* Location:              /Users/TJL4646/CustomerReview_Assignment/customerreviewserver.jar!/de/hybris/platform/customerreview/impl/DefaultCustomerReviewService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */