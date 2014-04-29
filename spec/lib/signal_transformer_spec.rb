require 'spec_helper'

describe SignalsTransformer do
  let(:signals) {
    { '2012-01' => 5,
      '2012-02' => 12,
      '2012-03' => 99
    }
  }
  subject{ SignalsTransformer.new(signals) }

  its(:fake_signals_count){ should == 1 }

  describe '#signals' do
    it 'should always contain length of format 2**n' do
      expect(subject.signals.length).to eq(4)
    end

    it 'fills missing signals with 0 values' do
      expect(subject.signals.values.last).to eq(0.0)
    end
  end

  describe "#c" do
    it 'should return mean value on first member' do
      subject.c(0).should == 29
    end

    it 'should return some value on second member' do # I'm unsure about this result
      subject.c(1).should == Complex('-23.5-3.0i')
    end

    it 'should return some value on third member' do # I'm unsure about this result
      subject.c(2).should == Complex('23.0+0.0i')
    end
  end

  describe '#fourier_signals' do
    it 'should be very similar to original signals' do
      fourier_signals = subject.fourier
      expect(fourier_signals.values[0].real).to be_within(0.000001).of(5)
      expect(fourier_signals.values[1].real).to be_within(0.000001).of(12)
      expect(fourier_signals.values[2].real).to be_within(0.000001).of(99)
      expect(fourier_signals.values[3].real).to be_within(0.000001).of(0)
    end
  end

  its(:power_spectrum){ should == "asdf"}
end