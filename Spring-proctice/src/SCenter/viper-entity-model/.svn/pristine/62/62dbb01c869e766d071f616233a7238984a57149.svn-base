package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 * 
 */
@Entity
@Table( name = "algorithmMetadata" )
@org.hibernate.annotations.Table( appliesTo = "algorithmMetadata", 
   indexes = { @Index( name = "ALGOR_METADATA_IX1", columnNames = { "relatedMarketItem_id" } ) } )
public class AlgorithmMetadataBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6050904490568093972L;

	@Id
    @GeneratedValue( generator = "algorithmMetadataBeanSequence" )
    @SequenceGenerator( name = "algorithmMetadataBeanSequence", sequenceName = "ALGORITHM_META_BEAN_SEQ" )
    private long id;

    private double score;
       
    @Column ( name = "BOUNTY_SCORE" )
    private double bountyScore;
    
    @Column ( name = "PAYMENT_RATE_SCORE" )
    private double paymentRateScore;
    
    @Column ( name = "INSTALL_RATE_SCORE" )
    private double installRateScore;
    
    @Column ( name = "PROMOTION_SCORE" )
    private double promotionScore;
    
    @Column ( name = "POPULARITY_SCORE" )
    private double popularityScore; 
    
    @ManyToOne
    @ForeignKey( name = "ALGORITHM_MD_FK01" )
    private MarketItemBean relatedMarketItem;

    @ManyToOne
    @ForeignKey( name = "ALGORITHM_MD_FK02" )
    private BundleBean relatedBundle;
    /**
     * Generic constructor for the AgentBean class.
     */
    public AlgorithmMetadataBean()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId( final long id )
    {
        this.id = id;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore( final double score )
    {
        this.score = score;
    }

    public MarketItemBean getRelatedMarketItem()
    {
        return relatedMarketItem;
    }

    public void setRelatedMarketItem( final MarketItemBean relatedMarketItem )
    {
        this.relatedMarketItem = relatedMarketItem;
    }

    public double getPopularityScore()
    {
        return popularityScore;
    }

    public void setPopularityScore( final double popularityScore )
    {
        this.popularityScore = popularityScore;
    }

    public double getInstallRateScore()
    {
        return installRateScore;
    }

    public void setInstallRateScore( final double installRateScore )
    {
        this.installRateScore = installRateScore;
    }

    public double getBountyScore()
    {
        return bountyScore;
    }

    public void setBountyScore( final double bountyScore )
    {
        this.bountyScore = bountyScore;
    }

    public double getPaymentRateScore()
    {
        return paymentRateScore;
    }

    public void setPaymentRateScore( final double paymentRateScore )
    {
        this.paymentRateScore = paymentRateScore;
    }

    public double getPromotionScore()
    {
        return promotionScore;
    }

    public void setPromotionScore( final double promotionScore )
    {
        this.promotionScore = promotionScore;
    }

    public BundleBean getRelatedBundle()
    {
        return relatedBundle;
    }

    public void setRelatedBundle( final BundleBean relatedBundle )
    {
        this.relatedBundle = relatedBundle;
    }

}
