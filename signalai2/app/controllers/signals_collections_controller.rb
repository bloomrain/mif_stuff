class SignalsCollectionsController < ApplicationController
  expose(:signals_collections)
  expose(:signals_collection)
  expose(:signals){ signals_collection.signals.formated }
  expose(:signals_collection_params){ params.require(:signals_collection).permit(:file, :url) }
  expose(:brawler){ Brawler.new(signals_collection.signals.formated) }
  expose(:min_random){ (params[:min_random] || -1.0).to_f }
  expose(:max_random){ (params[:max_random] || 1.0).to_f }
  expose(:noise_type){ params[:noise_type] || 'random_scope' }
  expose(:ck_noise_interval_from){ params[:ck_noise_interval_from].presence || 0 }
  expose(:ck_noise_interval_till){ params[:ck_noise_interval_till].presence || 0 }
  expose(:ck_noise_items){
    (ck_noise_interval_from.to_i..ck_noise_interval_till.to_i).to_a
  }
  expose(:ck_noise){
    noise.map.with_index do |noise_value, i|
      ck_noise_items.include?(i) ? noise_value : 1.0
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

  expose(:filter_ck_list){ (params[:filter_ck_list] || ["80"]).map(&:to_i) }

  expose(:default_chart_options) {
    {
      library: {
        dataOpacity: 0.0,
        fontSize: '15px',
        vAxis: {
          textStyle: {fontSize: '17px;'}
        },
        hAxis: {
          textStyle: {fontSize: '17px;'}
        }
      },
      height: '750px',
      colors: ['black'],
      discrete: true
    }
  }

  def create
    SignalsCollection.create!(signals_collection_params)
    redirect_to signals_collections_url
  end
end