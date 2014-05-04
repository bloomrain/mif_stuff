class SignalsCollectionsController < ApplicationController
  expose(:signals_collections)
  expose(:signals_collection)
  expose(:signals){ signals_collection.signals.formated }
  expose(:signals_collection_params){ params.require(:signals_collection).permit(:file, :url) }
  expose(:brawler){ Brawler.new(signals_collection.signals.formated) }
  expose(:min_random){ (params[:min_random] || 0.0).to_f }
  expose(:max_random){ (params[:max_random] || 200.0).to_f }
  expose(:noise_type){ params[:noise_type] || 'random_scope' }
  expose(:ck_noise_interval){ params[:ck_noise_interval].presence || "#{signals_collection.signals.fourier.size / 2} - #{signals_collection.signals.fourier.size}"}
  expose(:ck_noise_items){
    noise_from, noise_to = ck_noise_interval.to_s.split('-').map(&:to_i)
    (noise_from.to_i..noise_to.to_i).to_a
  }
  expose(:ck_noise){
    noise.map.with_index do |noise_value, i|
      ck_noise_items.include?(i) ? noise_value : 0.0
    end
  }
  expose(:noise){
    case noise_type
      when 'random_scope'
        brawler.random(min_random, max_random)
      when 'brownian'
        brawler.brownian
    end
  }

  expose(:filter_ck){ (params[:filter_ck] || 80).to_i }

  def create
    SignalsCollection.create!(signals_collection_params)
    redirect_to signals_collections_url
  end
end