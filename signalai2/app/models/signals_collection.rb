class SignalsCollection < ActiveRecord::Base
  serialize :signals, Array
  has_attached_file :file
  validates_attachment_content_type :file, :content_type => /\Atext\/.*\Z/

  structure do
    url :string
    file_file_name :string
    file_content_type :string
    file_file_size :integer
    file_updated_at :datetime
    signals :text
    timestamps
  end

  def signals_data(noise = [])
    return @signals_data unless @signals_data.nil?

    require 'csv'
    @signals_data = {}
    return @signals_data if file.path.blank?

    noise_index = 0
    CSV.parse(File.read(file.path), { headers: true }) do |row|
      @signals_data[row.to_hash.values.first] = row.to_hash.values.last.to_f
      @signals_data[row.to_hash.values.first] += noise[noise_index].to_f
      noise_index += 1
    end
    @signals_data
  end

  def signals(options = {})
    @signals ||= {}
    @signals[options.to_json] ||= SignalsTransformer.new(signals_data, options)
  end
end
